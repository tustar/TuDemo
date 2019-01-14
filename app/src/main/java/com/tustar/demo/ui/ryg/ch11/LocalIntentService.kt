package com.tustar.demo.ui.ryg.ch11

import android.app.IntentService
import android.content.Intent
import android.os.SystemClock
import com.tustar.common.util.Logger


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class LocalIntentService : IntentService("LocalIntentService") {

    companion object {
        private val TAG = LocalIntentService::class.java.simpleName
    }

    override fun onHandleIntent(intent: Intent) {
        val action = intent.getStringExtra("task_action")
        Logger.d(TAG, "receive task :" + action)
        SystemClock.sleep(3000)
        if ("com.ryg.action.TASK1" == action) {
            Logger.d(TAG, "handle task: " + action)
        }
    }
}
