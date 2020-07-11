package com.tustar.hencoder.ch3

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.annotation.GROUP_HEN_ID
import com.tustar.annotation.RowDemo
import com.tustar.hencoder.PracticeDrawActivity
import com.tustar.hencoder.R
import com.tustar.hencoder.R2

@RowDemo(groupId = GROUP_HEN_ID, name = R2.string.hen_practice_draw_3,
        actionId = R2.id.action_main_to_draw3)
class PracticeDraw3Activity : PracticeDrawActivity() {

    init {
        pageModels.add("DrawText")
        pageModels.add("StaticLayout")
        pageModels.add("TextSize")
        pageModels.add("Typeface")
        pageModels.add("FakeBoldText")
        pageModels.add("StrikeThruText")
        pageModels.add("UnderlineText")
        pageModels.add("TextSkewX")
        pageModels.add("TextScaleX")
        pageModels.add("TextAlign")
        pageModels.add("FontSpacing")
        pageModels.add("MeasureText")
        pageModels.add("TextBounds")
        pageModels.add("FontMetrics")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.hen_practice_draw_3)
    }

    override fun createFragment(position: Int): Fragment =
            PageFragment.newInstance(pageModels[position])
}
