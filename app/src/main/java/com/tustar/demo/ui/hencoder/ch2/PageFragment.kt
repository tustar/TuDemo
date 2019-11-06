package com.tustar.demo.ui.hencoder.ch2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.tustar.demo.R
import com.tustar.demo.ui.hencoder.ch2.practice.*
import com.tustar.demo.ui.hencoder.ch2.sample.*

class PageFragment : Fragment() {

    private lateinit var title: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parent = inflater.inflate(R.layout.fragment_page, container, false) as ViewGroup
        val practiceParent: RelativeLayout = parent.findViewById(R.id.page_practice)
        val sampleParent: RelativeLayout = parent.findViewById(R.id.page_sample)
        addPracticeView(practiceParent, sampleParent)

        return parent
    }

    private fun addPracticeView(practice: ViewGroup, sample: ViewGroup) {
        val childView = when (title) {
            "LinearGradient" -> Practice01LinearGradientView(context) to
                    Sample01LinearGradientView(context)
            "RadialGradient" -> Practice02RadialGradientView(context) to
                    Sample02RadialGradientView(context)
            "SweepGradient" -> Practice03SweepGradientView(context) to
                    Sample03SweepGradientView(context)
            "BitmapShader" -> Practice04BitmapShaderView(context) to
                    Sample04BitmapShaderView(context)
            "ComposeShader" -> Practice05ComposeShaderView(context) to
                    Sample05ComposeShaderView(context)
            "LightingColorFilter" -> Practice06LightingColorFilterView(context) to
                    Sample06LightingColorFilterView(context)
            "ColorMatrixColorFilter" -> Practice07ColorMatrixColorFilterView(context) to
                    Sample07ColorMatrixColorFilterView(context)
            "Xfermode" -> Practice08XfermodeView(context) to
                    Sample08XfermodeView(context)
            "StrokeCap" -> Practice09StrokeCapView(context) to
                    Sample09StrokeCapView(context)
            "StrokeJoin" -> Practice10StrokeJoinView(context) to
                    Sample10StrokeJoinView(context)
            "StrokeMiter" -> Practice11StrokeMiterView(context) to
                    Sample11StrokeMiterView(context)
            "PathEffect" -> Practice12PathEffectView(context) to
                    Sample12PathEffectView(context)
            "ShadowLayer" -> Practice13ShadowLayerView(context) to
                    Sample13ShadowLayerView(context)
            "MaskFilter" -> Practice14MaskFilterView(context) to
                    Sample14MaskFilterView(context)
            "FillPath" -> Practice15FillPathView(context) to
                    Sample15FillPathView(context)
            "TextPath" -> Practice16TextPathView(context) to
                    Sample16TextPathView(context)

            else -> {
                null
            }
        }
        childView?.let {
            val params = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT)
            practice.addView(it.first, params)
            sample.addView(it.second, params)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            title = args.getString("title")
        }
    }

    companion object {

        fun newInstance(title: String): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putString("title", title)
            fragment.arguments = args
            return fragment
        }
    }
}
