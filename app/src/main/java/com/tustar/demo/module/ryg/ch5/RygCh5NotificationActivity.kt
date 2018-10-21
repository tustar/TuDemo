package com.tustar.demo.module.ryg.ch5

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ryg_ch5_notification.*


class RygCh5NotificationActivity : BaseActivity() {

    private var sId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch5_notification)
        title = getString(R.string.ryg_ch5_notification)

        ryg_ch5_normal_btn.setOnClickListener {
            sId++
            var intent = Intent(this, RygCh5RemoteViewsActivity::class.java)
            var pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)
            var notification = NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Normal notification")
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("Chapter_5")
                    .setContentText("This is Normal notification")
                    .setContentIntent(pendingIntent)
                    .build()
            notification.flags = Notification.FLAG_AUTO_CANCEL
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(sId, notification)
        }

        ryg_ch5_custom_btn.setOnClickListener {
            sId++
            var intent = Intent(this, RygCh5RemoteViewsActivity::class.java)
            intent.putExtra("sid", "Custom Notification" + sId)
            var pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)
            var remoteViews = RemoteViews(packageName, R.layout.item_ryg_ch5_notification)
            remoteViews.setTextViewText(R.id.ryg_ch5_notification_msg, "Chapter_5" + sId)
            remoteViews.setImageViewResource(R.id.ryg_ch5_notification_icon, R.drawable.ryg_ch5_icon)
            remoteViews.setOnClickPendingIntent(R.id.ryg_ch5_notification_open, pendingIntent)
            var remoteIntent = Intent(this, RygCh5RemoteViewsActivity::class.java)
            var remotePendingIntent = PendingIntent.getActivity(this, 0, remoteIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT)
            var notification = NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ryg_ch5_icon)
                    .setTicker("Custom Notification")
                    .setWhen(System.currentTimeMillis())
                    .setCustomContentView(remoteViews)
                    .setContentIntent(remotePendingIntent)
                    .build()
            notification.flags = Notification.FLAG_AUTO_CANCEL
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(sId, notification)
        }
    }
}
