package com.tustar.hencoder.ch2

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tustar.annotation.GROUP_HEN_ID
import com.tustar.annotation.RowDemo
import com.tustar.hencoder.PracticeDrawActivity
import com.tustar.hencoder.R
import com.tustar.hencoder.R2

@RowDemo(groupId = GROUP_HEN_ID, name = R2.string.hen_practice_draw_2,
        actionId = R2.id.action_main_to_draw2)
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
        setTitle(R.string.hen_practice_draw_2)
    }

    override fun createFragment(position: Int): Fragment =
            PageFragment.newInstance(pageModels[position])
}
