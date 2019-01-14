package com.tustar.demo.ui.ryg.ch5

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.RemoteViews
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.ui.ryg.base.BaseRygActivity
import com.tustar.demo.ui.ryg.ch2.utils.MyConstants
import kotlinx.android.synthetic.main.activity_ryg_ch5.*


class RygCh5Activity : BaseRygActivity(), SimpleListItem1Adapter.OnItemClickListener {

    init {
        sClassList.add(RygCh5NotificationActivity::class.java)
        sClassList.add(RygCh5AppWidgetActivity::class.java)
        sClassList.add(RygCh5CustomActivity::class.java)
    }

    private var mRemoteViewsReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val remoteViews = intent.getParcelableExtra<RemoteViews>(MyConstants.EXTRA_REMOTE_VIEWS)
            if (remoteViews != null) {
                updateUI(remoteViews)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ryg_layout_id = R.layout.activity_ryg_ch5
        ryg_data_source = R.array.ryg_ch5_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch5_title)

        var intentFilter = IntentFilter(MyConstants.REMOTE_ACTION)
        registerReceiver(mRemoteViewsReceiver, intentFilter)
    }

    override fun onDestroy() {
        unregisterReceiver(mRemoteViewsReceiver)
        super.onDestroy()
    }

    private fun updateUI(remoteViews: RemoteViews) {
        val layoutId = resources.getIdentifier("item_ryg_ch5_simulated_notification", "layout",
                packageName)
        val view = layoutInflater.inflate(layoutId, ryg_ch5_remote_views_content, false)
        remoteViews.reapply(this, view)
        ryg_ch5_remote_views_content.addView(view)
    }
}