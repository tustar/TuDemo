package com.tustar.demo.ui.fm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.tustar.common.util.Logger
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_fm_open.*
import java.io.File
import java.lang.reflect.InvocationTargetException


class FmOpenActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private var beginPath: String = ""
    private lateinit var paths: MutableList<String>

    companion object {
        private val TAG = FmOpenActivity::class.java.simpleName
        private val ACTION_BROWSE = "com.zui.filemanager.BROWSE"
        private val BEGIN_PATH = "com.zui.filemanager.begin_path";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate ::")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm_open)
        title = getString(R.string.fm_open_file)

        paths = getStorageList();
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paths)
        fm_open_spinner.adapter = adapter
        fm_open_spinner.onItemSelectedListener = this
    }

    fun openInnerSdcardFolder(v: View) {
        var intent = Intent(ACTION_BROWSE)
        intent.putExtra(BEGIN_PATH, fm_open_edit_text.text.toString())
        startActivity(intent)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View,
                                pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        beginPath = paths[pos]
        fm_open_edit_text.setText(beginPath)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        beginPath = paths[0]
        fm_open_edit_text.setText(beginPath)
    }

    fun getStorageList(): MutableList<String> {
        var storages = mutableListOf<String>()
        storages.add(Environment.getExternalStorageDirectory().path)
        storages.add(Environment.getExternalStorageDirectory().path + "/Android")
        storages.add("gof/dg/adfa")
        try {
            val VolumeInfo_Class = Class.forName("android.os.storage.VolumeInfo")
            val DiskInfo_Class = Class.forName("android.os.storage.DiskInfo")
            var sm = getSystemService(Context.STORAGE_SERVICE)
            val StorageManager_getVolumes = sm.javaClass.getMethod("getVolumes")
            val VolumeInfo_getPath = VolumeInfo_Class.getMethod("getPath")
            val VolumeInfo_getType = VolumeInfo_Class.getMethod("getType")
            val VolumeInfo_getDisk = VolumeInfo_Class.getMethod("getDisk")
            val DiskInfo_isUsb = DiskInfo_Class.getMethod("isUsb")
            val DiskInfo_isSd = DiskInfo_Class.getMethod("isSd")
            val TYPE_PUBLIC = 0

            val List_VolumeInfo = StorageManager_getVolumes.invoke(sm) as List<Any>
            for (i in List_VolumeInfo.indices) {
                val volumeInfo = List_VolumeInfo[i]
                val file = VolumeInfo_getPath.invoke(volumeInfo) as File ?: continue
                val path = file.path
                Logger.d(TAG, "getStorageList :: $path")
                storages.add(path)

            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return storages
    }
}
