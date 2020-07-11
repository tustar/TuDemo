package com.tustar.qyz

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tustar.qyz.dragview.QyzDragViewActivity
import com.tustar.util.Logger
import com.tustar.widget.Decoration
import kotlinx.android.synthetic.main.activity_qyz_main.*

class QyzMainActivity : AppCompatActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = QyzMainActivity::class.java.simpleName
        private val sClassList = arrayListOf(
                QyzRotationActivity::class.java,
                QyzPaletteActivity::class.java,
                QyzTintingActivity::class.java,
                QyzElevationActivity::class.java,
                QyzClippingActivity::class.java,
                QyzAnimMainActivity::class.java,
                QyzSurfaceViewActivity::class.java,
                QyzSvgActivity::class.java,
                QyzFlexibleListViewActivity::class.java,
                QyzOverScrollGridViewActivity::class.java,
                QyzCustomWidgetActivity::class.java,
                QyzDragViewActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qyz_main)
        title = getString(R.string.qyz_main_title)

        qyz_main_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.qyz_list).toList()
        var adapter = SimpleListItem1Adapter(desc)
        qyz_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        qyz_main_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}
