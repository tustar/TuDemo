package com.tustar.demo.ui.hencoder.ch2

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.demo.ui.hencoder.PracticeDrawActivity
import com.tustar.demo.ui.hencoder.PageFragment


class PracticeDraw2Activity : PracticeDrawActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(com.tustar.demo.R.string.hen_practice_draw_1)
    }

    override fun createFragment(position: Int): Fragment =
            PageFragment.newInstance(pageModels[position])
}
