package com.tustar.ryg.ch6

import android.os.Bundle
import com.tustar.ryg.R
import com.tustar.ryg.BaseBookActivity

class RygCh6Activity : BaseBookActivity() {

    init {
        sClassList.add(RygCh6DrawableActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        book_data_source = R.array.ryg_ch6_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch6_title)
    }
}
