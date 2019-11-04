package com.tustar.demo.ui.hencoder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.tustar.demo.R
import com.tustar.demo.ui.hencoder.ch1.*

class PageFragment : Fragment() {

    private lateinit var title: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parent = inflater.inflate(R.layout.fragment_page, container, false) as ViewGroup
        addChildView(parent)

        return parent
    }

    private fun addChildView(parent: ViewGroup) {
        val childView = when (title) {
            "DrawColor" -> {
                Draw1ColorView(context!!)

            }
            "DrawCircle" -> {
                Draw2CircleView(context!!)
            }
            "DrawRect" -> {
                Draw3RectView(context!!)
            }
            "DrawPoint" -> {
                Draw4PointView(context!!)
            }
            "DrawOval" -> {
                Draw5OvalView(context!!)
            }
            "DrawLine" -> {
                Draw6LineView(context!!)
            }
            "DrawRoundRect" -> {
                Draw7RoundRectView(context!!)
            }
            "DrawArc" -> {
                Draw8ArcView(context!!)
            }
            "DrawPath" -> {
                Draw9PathView(context!!)
            }
            "Histogram" -> {
                Histogram10View(context!!)
            }
            "PieChart" -> {
                PieChart11View(context!!)
            }
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
