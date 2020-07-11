package com.tustar.hencoder.ch4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.tustar.hencoder.R
import com.tustar.hencoder.ch4.sample.*

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
            "ClipRect" -> Practice01ClipRectView(context) to
                    Sample01ClipRectView(context)
            "ClipPath" -> Practice02ClipPathView(context) to
                    Sample02ClipPathView(context)
            "Translate" -> Practice03TranslateView(context) to
                    Sample03TranslateView(context)
            "Scale" -> Practice04ScaleView(context) to
                    Sample04ScaleView(context)
            "Rotate" -> Practice05RotateView(context) to
                    Sample05RotateView(context)
            "Skew" -> Practice06SkewView(context) to
                    Sample06SkewView(context)
            "Matrix.translate" -> Practice07MatrixTranslateView(context) to
                    Sample07MatrixTranslateView(context)
            "Matrix.scale" -> Practice08MatrixScaleView(context) to
                    Sample08MatrixScaleView(context)
            "Matrix.rotate" -> Practice09MatrixRotateView(context) to
                    Sample09MatrixRotateView(context)
            "Matrix.skew" -> Practice10MatrixSkewView(context) to
                    Sample10MatrixSkewView(context)
            "Camera.Rotate" -> Practice11CameraRotateView(context) to
                    Sample11CameraRotateView(context)
            "Camera.rotate.fixed" -> Practice12CameraRotateFixedView(context) to
                    Sample12CameraRotateFixedView(context)
            "Camera.rotate.hitting.face" -> Practice13CameraRotateHittingFaceView(context) to
                    Sample13CameraRotateHittingFaceView(context)
            "Flipboard" -> Practice14FlipboardView(context) to
                    Sample14FlipboardView(context)
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
            title = args.getString("title")!!
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
