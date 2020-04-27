package com.tustar.ryg.ch3

import android.os.Bundle
import com.tustar.ryg.R
import com.tustar.ryg.BaseBookActivity
import com.tustar.ryg.SimpleListItem1Adapter

class RygCh3Activity : BaseBookActivity(), SimpleListItem1Adapter.OnItemClickListener {

    init {
        sClassList.add(RygCh3ViewMoveActivity::class.java)
        sClassList.add(RygCh3OuterActivity::class.java)
        sClassList.add(RygCh3InnerActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        book_data_source = R.array.ryg_ch3_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch3_title)
    }
}
