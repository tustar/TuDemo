package com.tustar.demo.module.ryg.ch5

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter

class RygCh5Activity : BaseRygActivity(), SimpleListItem1Adapter.OnItemClickListener {

    init {
        sClassList.add(RygCh5NotificationActivity::class.java)
        sClassList.add(RygCh5AppWidgetActivity::class.java)
        sClassList.add(RygCh5CustomActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ryg_data_source = R.array.ryg_ch5_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch5_title)
    }
}