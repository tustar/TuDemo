package com.tustar.demo.ui.fm

import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.tustar.util.Logger
import com.tustar.util.ToastUtils
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.widget.Decoration
import kotlinx.android.synthetic.main.activity_fm_rename.*
import java.io.File


class FmRenameActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = FmRenameActivity::class.java.simpleName
    }

    private var file: File? = null
    private lateinit var mFiles: List<File>
    private var mAdapter: SimpleListItem1Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate :: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm_rename)

        fm_rename_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        file = Environment.getExternalStorageDirectory()
        Logger.d(TAG, "onCreate :: file = $file")
        mFiles = getFiles()
        Logger.d(TAG, "onCreate :: listFiles = ${file!!.listFiles()}")
        Logger.d(TAG, "onCreate :: mFiles = $mFiles")
        mAdapter = SimpleListItem1Adapter(mFiles.map { it -> it.name })
        fm_rename_rv.adapter = mAdapter
        mAdapter!!.setOnItemClickListener(this)
        fm_rename_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val editText = EditText(this)
        var oldFile = mFiles[position]
        androidx.appcompat.app.AlertDialog.Builder(this).setTitle(R.string.rename)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setView(editText)
                .setPositiveButton(android.R.string.ok, { dialog, _ ->
                    var text = editText.text.toString().trim()
                    if (!TextUtils.isEmpty(text)) {
                        var newFile = File(oldFile.parent + File.separator + text)
                        Logger.d(TAG, "${oldFile.path} => ${newFile.path}")
                        if (newFile.exists()) {
                            ToastUtils.showLong(DeskRenameActivity@ this, "${newFile.path} exist")
                        }
                        if (oldFile.renameTo(newFile)) {
                            ToastUtils.showLong(DeskRenameActivity@ this, "Rename Success")
                            mFiles = getFiles()
                            mAdapter!!.data = mFiles.map { it -> it.name }
                            mAdapter!!.notifyDataSetChanged()
                        } else {
                            ToastUtils.showLong(DeskRenameActivity@ this, "Rename failure")
                        }
                    }
                    dialog.dismiss()
                })
                .setNegativeButton(android.R.string.cancel, { dialog, _ -> dialog.dismiss() })
                .show()
        editText.setText(oldFile.name)
    }

    private fun getFiles() = file!!.listFiles()
            .sortedBy { it -> it.lastModified() }
}
