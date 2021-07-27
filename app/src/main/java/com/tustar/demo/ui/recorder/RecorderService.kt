package com.tustar.demo.ui.recorder

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaRecorder
import android.media.MediaRecorder.*
import android.net.Uri
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.tustar.demo.util.Logger
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*


class RecorderService : Service() {

    private lateinit var recorder: MediaRecorder
    private lateinit var recordingUri: Uri
    private var fd: FileDescriptor? = null
    private var state: State = State.IDLE
    private val recording: Boolean
        get() = state in arrayOf(State.RECORDING, State.RECORDING_BY_RESUME)

    //
    private var onRecorderListeners: ArrayList<WeakReference<OnRecorderListener>> = arrayListOf()
    private var handler = Handler(Looper.getMainLooper())
    private val onMaxAmplitudeCaller: Runnable = object : Runnable {
        override fun run() {
            for (listener in onRecorderListeners) {
                listener.get()?.onRecorderChanged(RecorderInfo(state, recorder.maxAmplitude))
                if (!recording) {
                    return
                }
            }
            if (recording) {
                handler.postDelayed(this, MAX_AMPLITUDE_UPDATE_PERIOD)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return RecorderBinder()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            ACTION_START_RECORDING -> handleStartRecorder()
            ACTION_PAUSE_RECORDING -> handlePauseRecorder()
            ACTION_RESUME_RECORDING -> handleResumeRecorder()
            ACTION_STOP_RECORDING -> handleStopRecorder()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleStartRecorder(highQuality: Boolean = true) {
        if (recording) {
            Logger.w("Recording...")
            return
        }
        val uri = RecorderDao.create(this, displayName(), AUDIO_AAC)
        if (uri == null) {
            Logger.w("Create recorder uri failure!")
            return
        }
        recordingUri = uri

        try {
            val pdf = contentResolver.openFileDescriptor(recordingUri, "w")
            if (pdf == null) {
                Logger.w("ParcelFileDescriptor is null!")
                return
            }
            fd = pdf.fileDescriptor
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        //
        recorder = MediaRecorder().apply {
            setAudioSource(AudioSource.MIC)
            setOutputFile(fd)
            setAudioEncodingBitRate(if (highQuality) DEFAULT_BIT_RATE else MIN_BIT_RATE)
            setAudioSamplingRate(if (highQuality) DEFAULT_SAMPLE_RATE else MIN_SAMPLE_RATE)
            setAudioChannels(DEFAULT_CHANNEL)
            setOutputFormat(OutputFormat.AAC_ADTS)
            setAudioEncoder(AudioEncoder.AAC)
        }
        try {
            recorder.prepare()
            recorder.start()
            updateRecordState(State.RECORDING)
        } catch (e: Exception) {
            RecorderDao.delete(this, recordingUri)
            releaseRecorder()
        }
    }

    private fun handlePauseRecorder() {
        if (!recording) {
            Logger.w("Recorder is stopped!")
            return
        }

        recorder.pause()
        updateRecordState(State.RECORDING_PAUSE)
    }

    private fun handleResumeRecorder() {
        if (recording) {
            Logger.w("Recording...")
            return
        }

        recorder.resume()
        updateRecordState(State.RECORDING_BY_RESUME)
    }

    private fun handleStopRecorder() {
        //
        if (!recording) {
            Logger.w("Recorder is stopped!")
            return
        }
        //
        recorder.stop()
        RecorderDao.updatePending(this, recordingUri)
        updateRecordState(State.IDLE)
        //
        releaseRecorder()
    }

    private fun releaseRecorder() {
        recorder.reset()
        recorder.release()
    }

    fun addOnRecorderListener(listener: OnRecorderListener) =
        onRecorderListeners.add(WeakReference(listener))


    fun removeOnRecorderListener(listener: OnRecorderListener) =
        onRecorderListeners.removeIf { it.get() == listener }

    private fun updateRecordState(state: State) {
        this.state = state
        for (listener in onRecorderListeners) {
            listener.get()?.onRecorderChanged(RecorderInfo(state, recorder.maxAmplitude))
        }
        handler.removeCallbacks(onMaxAmplitudeCaller)
        handler.post(onMaxAmplitudeCaller)
    }

    inner class RecorderBinder : Binder() {
        fun getService(): RecorderService = this@RecorderService
    }

    companion object {

        private const val DEFAULT_SAMPLE_RATE = 48000
        private const val DEFAULT_BIT_RATE = 156000
        private const val DEFAULT_CHANNEL = 2
        private const val MIN_SAMPLE_RATE = 8000
        private const val MIN_BIT_RATE = 12200
        private const val AUDIO_AAC = "audio/aac-adts"

        //
        private const val MAX_AMPLITUDE_UPDATE_PERIOD = 100L
        private const val REFERENCE = 0.00002
        const val MAX_AMPLITUDE_DEFAULT = 4


        //
        private const val ACTION_PREFIX = "com.tustar.demo.action"
        private const val ACTION_START_RECORDING = "$ACTION_PREFIX.START_RECORDING"
        private const val ACTION_PAUSE_RECORDING = "$ACTION_PREFIX.PAUSE_RECORDING"
        private const val ACTION_RESUME_RECORDING = "$ACTION_PREFIX.RESUME_RECORDING"
        private const val ACTION_STOP_RECORDING = "$ACTION_PREFIX.STOP_RECORDING"

        fun displayName(): String {
            val dataFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
            return "REC_" + dataFormat.format(Calendar.getInstance().time)
        }

        fun startRecording(context: Context) {
            actionRecording(context, ACTION_START_RECORDING)
        }

        fun pauseRecording(context: Context) {
            actionRecording(context, ACTION_PAUSE_RECORDING)
        }

        fun resumeRecording(context: Context) {
            actionRecording(context, ACTION_RESUME_RECORDING)
        }

        fun stopRecording(context: Context) {
            actionRecording(context, ACTION_STOP_RECORDING)
        }

        private fun actionRecording(context: Context, action: String) {
            val intent = Intent(action).apply {
                setClass(context, RecorderService::class.java)
            }
            context.startService(intent)
        }

        fun bindService(context: Context, conn: ServiceConnection) {
            val intent = Intent().apply {
                setClass(context, RecorderService::class.java)
            }
            context.bindService(intent, conn, Context.BIND_AUTO_CREATE)
        }
    }
}