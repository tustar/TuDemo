package com.tustar.hencoder.ch4

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.annotation.GROUP_HEN_ID
import com.tustar.annotation.RowDemo
import com.tustar.hencoder.PracticeDrawActivity
import com.tustar.hencoder.R
import com.tustar.hencoder.R2

@RowDemo(groupId = GROUP_HEN_ID, name = R2.string.hen_practice_draw_4,
        actionId = R2.id.action_main_to_draw4)
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
        setTitle(R.string.hen_practice_draw_3)
    }

    override fun createFragment(position: Int): Fragment =
            PageFragment.newInstance(pageModels[position])
}
