package com.tustar.demo.ui.weather


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver.SCHEME_ANDROID_RESOURCE
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tustar.demo.R
import com.tustar.demo.data.remote.Now
import com.tustar.demo.ui.KEY_TAB_INDEX_SELECTED
import com.tustar.demo.ui.MainActivity
import com.tustar.demo.ui.TAB_INDEX_WEATHER

object WeatherNotification {

    const val WEATHER_CHANNEL_ID = "WEATHER_CHANNEL_ID"

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = context.getString(R.string.weather_channel_name)
            val description: String = context.getString(R.string.weather_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(WEATHER_CHANNEL_ID, name, importance)
            channel.description = description
//            val audioAttributes = AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
//                .build()
//            channel.setSound(getSound(context), audioAttributes)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager: NotificationManager = context.getSystemService(
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getSound(context: Context): Uri = Uri.parse(
        "$SCHEME_ANDROID_RESOURCE://" +
                "${context.packageName}/${R.raw.voicetips}"
    )

    fun notify(context: Context, now: Now, location: String) {

        val notificationId = 0x1
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(KEY_TAB_INDEX_SELECTED, TAB_INDEX_WEATHER)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notification = NotificationCompat.Builder(context, WEATHER_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(location)
            .setContentText(now.toContent(context))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setSound(getSound(context))
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, notification)
        }
    }

    fun postNotification(context: Context, now: Now, location: String) {
        createNotificationChannel(context)
        notify(context, now, location)
    }
}