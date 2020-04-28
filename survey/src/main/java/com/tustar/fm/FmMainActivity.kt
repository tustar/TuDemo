package com.tustar.fm

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tustar.fm.service.FileMonitorService
import com.tustar.util.Logger
import com.tustar.widget.Decoration
import kotlinx.android.synthetic.main.activity_fm_main.*

class FmMainActivity : BaseActivity(), SimpleListAdapter.OnItemClickListener {

    companion object {
        private val TEST_GIF = "/storage/emulated/0/img-9d0d09ably1fgslbs4vu3g20b205z7wm.gif"
        private val TAG = FmMainActivity::class.java.simpleName
        private val sClassList = ArrayList<Class<*>>()

        // List
        init {
            sClassList.run {
                add(FmGifPlayerActivity::class.java)
                add(FmGifDrawableActivity::class.java)
                add(FmRenameActivity::class.java)
                add(FmSpShareActivity::class.java)
                add(FmOpenActivity::class.java)
                add(FmSectionListViewActivity::class.java)
                add(FmArcActivity::class.java)
                add(FmLongPhotoActivity::class.java)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm_main)
        title = getString(R.string.fm_main_title)

        fm_main_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.fm_list).toList()
        var adapter = SimpleListAdapter(desc)
        fm_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        fm_main_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))

        // 监听文件变化
        FileMonitorService.startMonitor(this)
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        if (clazz == FmGifPlayerActivity::class.java) {
            intent.putExtra(FmGifPlayerActivity.GIF_FILE_PATH, TEST_GIF)
        }
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}
