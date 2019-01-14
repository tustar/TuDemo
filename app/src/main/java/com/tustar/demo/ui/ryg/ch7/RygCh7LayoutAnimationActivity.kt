package com.tustar.demo.ui.ryg.ch7

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ArrayAdapter
import android.widget.Toast
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ryg_ch3_outer.*
import kotlinx.android.synthetic.main.item_ryg_ch3_outer_layout.view.*


class RygCh7LayoutAnimationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch3_outer)
        title = getString(R.string.ryg_ch7_layout_animation)

        for (i in 1..3) {
            var viewGroup = layoutInflater.inflate(R.layout.item_ryg_ch3_outer_layout, ryg_ch3_outer,
                    false) as ViewGroup
            viewGroup.layoutParams.width = resources.displayMetrics.widthPixels
            viewGroup.item_title.text = "page $i"
            viewGroup.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0))
            createList(viewGroup)
            ryg_ch3_outer.addView(viewGroup)
        }
    }

    private fun createList(viewGroup: ViewGroup) {

        val animation = AnimationUtils.loadAnimation(this, R.anim.ryg_ch7_item_anim)
        val controller = LayoutAnimationController(animation)
        controller.delay = 0.5f
        controller.order = LayoutAnimationController.ORDER_NORMAL
        viewGroup.item_list_view.layoutAnimation = controller

        val datas = ArrayList<String>()
        for (i in 0..49) {
            datas.add("name " + i)
        }

        viewGroup.item_list_view.adapter = ArrayAdapter(this,
                R.layout.item_ryg_ch3_list_item, R.id.item_name, datas)
        viewGroup.item_list_view.setOnItemClickListener { _, _, _, _ ->
            Toast.makeText(this@RygCh7LayoutAnimationActivity, "click item",
                Toast.LENGTH_SHORT).show()}
    }
}
