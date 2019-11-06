package com.tustar.demo.ui.hencoder.ch4

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.demo.ui.hencoder.PracticeDrawActivity


class PracticeDraw4Activity : PracticeDrawActivity() {

    init {
        pageModels.add("ClipRect")
        pageModels.add("ClipPath")
        pageModels.add("Translate")
        pageModels.add("Scale")
        pageModels.add("Rotate")
        pageModels.add("Skew")
        pageModels.add("Matrix.translate")
        pageModels.add("Matrix.scale")
        pageModels.add("Matrix.rotate")
        pageModels.add("Matrix.skew")
        pageModels.add("Camera.Rotate")
        pageModels.add("Camera.rotate.fixed")
        pageModels.add("Camera.rotate.hitting.face")
        pageModels.add("Flipboard")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(com.tustar.demo.R.string.hen_practice_draw_3)
    }

    override fun createFragment(position: Int): Fragment =
            PageFragment.newInstance(pageModels[position])
}
