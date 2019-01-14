package com.tustar.demo.ui.fm.service

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Messenger
import android.provider.MediaStore
import com.tustar.demo.ui.fm.MediaStoreContentObserver
import java.util.*
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * Created by tustar on 17-7-12.
 */
class FileMonitorService : Service() {

    companion object {
        fun startMonitor(context: Context) {
            context.startService(Intent(context, FileMonitorService::class.java))
        }
    }

    private var mObservers = ArrayList<MediaStoreContentObserver>()
    private val mMessenger = Messenger(Handler())
    private val mHandler = Handler()
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerObservers()
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder {
        return mMessenger.binder
    }

    override fun onCreate() {
        super.onCreate()

        //
        bindService(Intent(this, FileScannerService::class.java), mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnection)

        unregisterObservers()
    }

    fun registerObservers() {
        var executor = ScheduledThreadPoolExecutor(0)
        executor.maximumPoolSize = 1
        executor.setKeepAliveTime(60L, TimeUnit.SECONDS)
        mObservers.add(MediaStoreContentObserver(mHandler, this, executor,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        mObservers.add(MediaStoreContentObserver(mHandler, this, executor,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI))
        mObservers.add(MediaStoreContentObserver(mHandler, this, executor,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI))
        mObservers.add(MediaStoreContentObserver(mHandler, this, executor,
                MediaStore.Files.getContentUri("external")))
    }

    fun unregisterObservers() {
        if (mObservers.isNotEmpty()) {
            mObservers.forEach { contentResolver.unregisterContentObserver(it) }
        }
    }
}