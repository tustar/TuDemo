package com.tustar.demo.ui.custom

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.media.MediaRecorder.*
import android.net.Uri
import android.os.IBinder
import androidx.compose.runtime.State
import com.tustar.demo.util.Logger
import dagger.hilt.android.scopes.ServiceScoped
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*


@ServiceScoped
class RecorderService : Service() {

    private lateinit var recorder: MediaRecorder
    private lateinit var recordingUri: Uri
    private var fd: FileDescriptor? = null
    private var recording: Boolean = false

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            ACTION_START_RECORDING -> handleStartRecorder()
            ACTION_STOP_RECORDING -> handleStopRecorder()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleStartRecorder(highQuality: Boolean = true) {
        if (recording) {
            Logger.d("Recording...")
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
            recording = true
        } catch (e: Exception) {
            RecorderDao.delete(this, recordingUri)
        }
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
        recording = false
    }

    private fun releaseRecorder() {
        recorder.reset()
        recorder.release()
    }

    companion object {

        private const val DEFAULT_SAMPLE_RATE = 48000
        private const val DEFAULT_BIT_RATE = 156000
        private const val DEFAULT_CHANNEL = 2
        private const val MIN_SAMPLE_RATE = 8000
        private const val MIN_BIT_RATE = 12200
        private const val AUDIO_AAC = "audio/aac-adts"

        //
        private const val ACTION_START_RECORDING = "com.tustar.demo.action.START_RECORDING"
        private const val ACTION_STOP_RECORDING = "com.tustar.demo.action.STOP_RECORDING"

        //
        private const val INTENT_KEY_AMP_STATE = "apm_state"

        fun displayName(): String {
            val dataFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
            return "REC_" + dataFormat.format(Calendar.getInstance().time)
        }

        fun startRecording(context: Context) {
            val intent = Intent(ACTION_START_RECORDING).apply {
                setClass(context, RecorderService::class.java)
            }
            context.startService(intent)
        }

        fun stopRecording(context: Context) {
            val intent = Intent(ACTION_STOP_RECORDING).apply {
                setClass(context, RecorderService::class.java)
            }
            context.startService(intent)
        }
    }
}