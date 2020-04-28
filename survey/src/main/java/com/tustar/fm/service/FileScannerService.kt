package com.tustar.fm.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Messenger

/**
 * Created by tustar on 17-7-12.
 */
class FileScannerService : Service() {

    private val mMessenger = Messenger(Handler())

    override fun onBind(intent: Intent?): IBinder {
        return mMessenger.binder
    }
}