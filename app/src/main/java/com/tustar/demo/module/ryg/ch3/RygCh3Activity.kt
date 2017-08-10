package com.tustar.demo.module.ryg.ch3

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.module.ryg.ch5.BaseRygActivity

class RygCh3Activity : BaseRygActivity(), SimpleListItem1Adapter.OnItemClickListener {

    init {
        sClassList.add(RygCh3ViewMoveActivity::class.java)
        sClassList.add(RygCh3OuterActivity::class.java)
        sClassList.add(RygCh3InnerActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        ryg_data_source = R.array.ryg_ch3_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch3_title)
    }
}
