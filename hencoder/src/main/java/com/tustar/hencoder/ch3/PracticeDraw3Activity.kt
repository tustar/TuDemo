package com.tustar.hencoder.ch3

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.hencoder.PracticeDrawActivity
import com.tustar.hencoder.R


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
