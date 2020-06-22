package com.tustar.hencoder.ch1


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.annotation.GROUP_HEN_ID
import com.tustar.annotation.RowDemo
import com.tustar.hencoder.PracticeDrawActivity
import com.tustar.hencoder.R

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

//    @RowDemo(groupId = GROUP_HEN_ID, name = R.string.hen_practice_draw_1, actionId = 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.hen_practice_draw_1)
    }

    override fun createFragment(position: Int): Fragment =
            PageFragment.newInstance(pageModels[position])
}
