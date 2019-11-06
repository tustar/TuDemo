package com.tustar.demo.ui.hencoder.ch2

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.demo.ui.hencoder.PracticeDrawActivity


class PracticeDraw2Activity : PracticeDrawActivity() {

    init {
        pageModels.add("LinearGradient")
        pageModels.add("RadialGradient")
        pageModels.add("SweepGradient")
        pageModels.add("BitmapShader")
        pageModels.add("ComposeShader")
        pageModels.add("LightingColorFilter")
        pageModels.add("ColorMatrixColorFilter")
        pageModels.add("Xfermode")
        pageModels.add("StrokeCap")
        pageModels.add("StrokeJoin")
        pageModels.add("StrokeMiter")
        pageModels.add("PathEffect")
        pageModels.add("ShadowLayer")
        pageModels.add("MaskFilter")
        pageModels.add("FillPath")
        pageModels.add("TextPath")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(com.tustar.demo.R.string.hen_practice_draw_2)
    }

    override fun createFragment(position: Int): Fragment =
            PageFragment.newInstance(pageModels[position])
}
