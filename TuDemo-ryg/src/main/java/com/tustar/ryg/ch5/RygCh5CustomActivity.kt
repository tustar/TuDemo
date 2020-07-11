package com.tustar.ryg.ch5

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.RemoteViews
import com.tustar.ryg.R
import androidx.appcompat.app.AppCompatActivity
import com.tustar.ryg.ch2.utils.MyConstants


class RygCh5CustomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch5_custom)
        title = getString(R.string.ryg_ch5_custom)
    }

    fun onButtonClick(view: View) {
        val remoteViews = RemoteViews(packageName, R.layout.item_ryg_ch5_simulated_notification)
        remoteViews.setImageViewBitmap(R.id.ryg_ch5_sim_icon, BitmapFactory
                .decodeResource(resources, R.drawable.ryg_ch5_icon))
        remoteViews.setTextViewText(R.id.ryg_ch5_sim_msg, "Message from process: " +
                "${Process.myPid()}")
        val itemHolderIntent = Intent(this, RygCh5RemoteViewsActivity::class.java)
        itemHolderIntent.putExtra("sid", "Item Holder")
        val pendingIntent = PendingIntent.getActivity(this, 0,
                itemHolderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val openIntent = Intent(this, RygCh5CustomActivity::class.java)
        val openPendingIntent = PendingIntent.getActivity(
                this, 0, openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.ryg_ch5_sim_item_holder, pendingIntent)
        remoteViews.setOnClickPendingIntent(R.id.ryg_ch5_sim_open, openPendingIntent)
        val intent = Intent(MyConstants.REMOTE_ACTION)
        intent.putExtra(MyConstants.EXTRA_REMOTE_VIEWS, remoteViews)
        sendBroadcast(intent)
        finish()
    }
}
