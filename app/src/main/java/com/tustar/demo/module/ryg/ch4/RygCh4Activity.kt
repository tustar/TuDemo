package com.tustar.demo.module.ryg.ch4

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.module.ryg.base.BaseRygActivity

class RygCh4Activity : BaseRygActivity(), SimpleListItem1Adapter.OnItemClickListener {
    init {
        sClassList.add(RygCh4WidthHeightActivity::class.java)
        sClassList.add(RygCh4CircleViewActivity::class.java)
        sClassList.add(RygCh4ExActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        ryg_data_source = R.array.ryg_ch4_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch4_title)
    }
}
