package com.tustar.ryg.ch12

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.tustar.ryg.R
import com.tustar.ryg.ch12.loader.ImageLoader
import com.tustar.ryg.ch12.provider.Images
import com.tustar.util.DeviceUtils
import kotlinx.android.synthetic.main.activity_ryg_ch12.*


class RygCh12Activity : AppCompatActivity(), AbsListView.OnScrollListener {

    companion object {
        private val TAG = RygCh12Activity::class.java.simpleName
    }


    private lateinit var mImageLoader: ImageLoader
    private lateinit var mImageAdapter: BaseAdapter

    private var mIsGridViewIdle = true
    private var mImageWidth = 0
    private var mIsWifi = false
    private var mCanGetBitmapFromNetWork = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch12)
        title = getString(R.string.ryg_ch12_title)

        initData()
        initView()
        mImageLoader = ImageLoader.build(this)
    }

    private fun initData() {
        val screenWidth = DeviceUtils.getScreenMetrics(this).widthPixels
        val space = DeviceUtils.dp2px(this, 20f).toInt()
        mImageWidth = (screenWidth - space) / 3
        mIsWifi = DeviceUtils.isWifi(this)
        if (mIsWifi) {
            mCanGetBitmapFromNetWork = true
        }
    }

    private fun initView() {
        mImageAdapter = ImageAdapter(this, Images.imageUrls)
        ryg_ch12_grid_view.adapter = mImageAdapter
        ryg_ch12_grid_view.setOnScrollListener(this)

        if (!mIsWifi) {
            val builder = androidx.appcompat.app.AlertDialog.Builder(this)
            builder.setMessage("初次使用会从网络下载大概5MB的图片，确认要下载吗？")
            builder.setTitle("注意")
            builder.setPositiveButton("是") { _, _ ->
                mCanGetBitmapFromNetWork = true
                mImageAdapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("否", null)
            builder.show()
        }
    }

    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int,
                          totalItemCount: Int) {

    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mIsGridViewIdle = true
            mImageAdapter.notifyDataSetChanged()
        } else {
            mIsGridViewIdle = false
        }
    }

    private inner class ImageAdapter() : BaseAdapter() {

        private lateinit var mInflater: LayoutInflater
        private lateinit var mDefaultBitmapDrawable: Drawable
        private lateinit var mUrls: Array<String>

        @SuppressLint("NewApi")
        constructor(context: Context, urls: Array<String>) : this() {
            mInflater = LayoutInflater.from(context)
            mDefaultBitmapDrawable = context.resources.getDrawable(
                    R.drawable.ryg_ch12_image_default, null)
            mUrls = urls
        }

        override fun getCount(): Int {
            return if (mUrls != null) mUrls.size else 0

        }

        override fun getItem(position: Int): String {
            return mUrls[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var holder: ViewHolder
            var view: View? = convertView
            if (convertView == null) {
                view = mInflater.inflate(R.layout.item_ryg_ch12_grid_view_item, parent,
                        false)
                holder = ViewHolder(view)
                view.tag = holder
            } else {
                holder = convertView.tag as ViewHolder
            }

            val imageView = holder.imageView
            if (imageView != null) {
                val tag = imageView.tag
                val url = getItem(position)
                if (url != tag) {
                    imageView.setImageDrawable(mDefaultBitmapDrawable)
                }
                if (mIsGridViewIdle && mCanGetBitmapFromNetWork) {
                    imageView.tag = url
                    mImageLoader.bindBitmap(url, imageView, mImageWidth, mImageWidth)
                }
            }

            return view
        }
    }

    private class ViewHolder(view: View) {
        var imageView: ImageView? = null

        init {
            imageView = view.findViewById(R.id.ryg_ch12_item_image) as ImageView
        }
    }
}
