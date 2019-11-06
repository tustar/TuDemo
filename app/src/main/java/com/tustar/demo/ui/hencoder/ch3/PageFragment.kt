package com.tustar.demo.ui.hencoder.ch3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.tustar.demo.R
import com.tustar.demo.ui.hencoder.ch2.Practice15FillPathView
import com.tustar.demo.ui.hencoder.ch2.Practice16TextPathView

class PageFragment : Fragment() {

    private lateinit var title: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parent = inflater.inflate(R.layout.fragment_page, container, false) as ViewGroup
        addChildView(parent)

        return parent
    }

    private fun addChildView(parent: ViewGroup) {
        val childView = when (title) {
            "DrawText" -> Practice01DrawTextView(context!!)
            "StaticLayout" -> Practice02StaticLayoutView(context!!)
            "TextSize" -> Practice03SetTextSizeView(context!!)
            "Typeface" -> Practice04SetTypefaceView(context!!)
            "FakeBoldText" -> Practice05SetFakeBoldTextView(context!!)
            "StrikeThruText" -> Practice06SetStrikeThruTextView(context!!)
            "UnderlineText" -> Practice07SetUnderlineTextView(context!!)
            "TextSkewX" -> Practice08SetTextSkewXView(context)
            "TextScaleX" -> Practice09SetTextScaleXView(context)
            "TextAlign" -> Practice10SetTextAlignView(context)
            "FontSpacing" -> Practice11GetFontSpacingView(context)
            "MeasureText" -> Practice12MeasureTextView(context)
            "TextBounds" -> Practice13GetTextBoundsView(context)
            "FontMetrics" -> Practice14GetFontMetricsView(context)
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
