package com.tustar.demo.ui.hencoder.ch1


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.demo.R
import com.tustar.demo.ui.hencoder.PracticeDrawActivity

class PracticeDraw1Activity : PracticeDrawActivity() {

    init {
        pageModels.add("DrawColor")
        pageModels.add("DrawCircle")
        pageModels.add("DrawRect")
        pageModels.add("DrawPoint")
        pageModels.add("DrawOval")
        pageModels.add("DrawLine")
        pageModels.add("DrawRoundRect")
        pageModels.add("DrawArc")
        pageModels.add("DrawPath")
        pageModels.add("Histogram")
        pageModels.add("PieChart")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.hen_practice_draw_1)
    }

    override fun createFragment(position: Int): Fragment =
            PageFragment.newInstance(pageModels[position])
}
