package com.tustar.demo.ui.hencoder.ch3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.tustar.demo.R
import com.tustar.demo.ui.hencoder.ch3.practice.*
import com.tustar.demo.ui.hencoder.ch3.sample.*

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
            "DrawText" -> Practice01DrawTextView(context) to
                    Sample01DrawTextView(context)
            "StaticLayout" -> Practice02StaticLayoutView(context) to
                    Sample02StaticLayoutView(context)
            "TextSize" -> Practice03SetTextSizeView(context) to
                    Sample03SetTextSizeView(context)
            "Typeface" -> Practice04SetTypefaceView(context) to
                    Sample04SetTypefaceView(context)
            "FakeBoldText" -> Practice05SetFakeBoldTextView(context) to
                    Sample05SetFakeBoldTextView(context)
            "StrikeThruText" -> Practice06SetStrikeThruTextView(context) to
                    Sample06SetStrikeThruTextView(context)
            "UnderlineText" -> Practice07SetUnderlineTextView(context) to
                    Sample07SetUnderlineTextView(context)
            "TextSkewX" -> Practice08SetTextSkewXView(context) to
                    Sample08setTextSkewXView(context)
            "TextScaleX" -> Practice09SetTextScaleXView(context) to
                    Sample09SetTextScaleXView(context)
            "TextAlign" -> Practice10SetTextAlignView(context) to
                    Sample10SetTextAlignView(context)
            "FontSpacing" -> Practice11GetFontSpacingView(context) to
                    Sample11GetFontSpacingView(context)
            "MeasureText" -> Practice12MeasureTextView(context) to
                    Sample12MeasureTextView(context)
            "TextBounds" -> Practice13GetTextBoundsView(context) to
                    Sample13GetTextBoundsView(context)
            "FontMetrics" -> Practice14GetFontMetricsView(context) to
                    Sample14GetFontMetricsView(context)
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
