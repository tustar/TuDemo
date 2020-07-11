package com.tustar.ryg.ch8

import android.os.Bundle
import com.tustar.ryg.R
import com.tustar.ryg.BaseBookActivity

class RygCh8Activity : BaseBookActivity() {


    init {
        sClassList.add(RygCh8FloatActivity::class.java)
        sClassList.add(RygCh8DialogActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        book_data_source = R.array.ryg_ch8_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch8_title)
    }
}
