package com.tustar.demo.ui.hencoder.ch2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.tustar.demo.R

class PageFragment : Fragment() {

    private lateinit var title: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parent = inflater.inflate(R.layout.fragment_page, container, false) as ViewGroup
        addChildView(parent)

        return parent
    }

    private fun addChildView(parent: ViewGroup) {
        val childView = when (title) {
            "LinearGradient" -> Practice01LinearGradientView(context!!)
            "RadialGradient" -> Practice02RadialGradientView(context!!)
            "SweepGradient" -> Practice03SweepGradientView(context!!)
            "BitmapShader" -> Practice04BitmapShaderView(context!!)
            "ComposeShader" -> Practice05ComposeShaderView(context!!)
            "LightingColorFilter" -> Practice06LightingColorFilterView(context!!)
            "ColorMatrixColorFilter" -> Practice07ColorMatrixColorFilterView(context!!)
            "Xfermode" -> Practice08XfermodeView(context!!)
            "StrokeCap" -> Practice09StrokeCapView(context!!)
            "StrokeJoin" -> Practice10StrokeJoinView(context!!)
            "StrokeMiter" -> Practice11StrokeMiterView(context!!)
            "PathEffect" -> Practice12PathEffectView(context!!)
            "ShadowLayer" -> Practice13ShadowLayerView(context!!)
            "MaskFilter" -> Practice14MaskFilterView(context!!)
            "FillPath" -> Practice15FillPathView(context!!)
            "TextPath" -> Practice16TextPathView(context!!)

            else -> {
                null
            }
        }
        childView?.let {
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            params.weight = 1F
            parent.addView(it, params)
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
