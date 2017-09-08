package com.tustar.demo.module.ryg.ch12.loader

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.LruCache
import android.widget.ImageView
import com.tustar.demo.R
import com.tustar.demo.util.Logger
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


/**
 * Created by tustar on 17-8-31.
 */
class ImageLoader {

    companion object {
        private val TAG = ImageLoader::class.java.simpleName
        private val MESSAGE_POST_RESULT = 1

        // Executor
        private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        private val CORE_POOL_SIZE = CPU_COUNT + 1
        private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
        private val KEEP_ALIVE = 10L
        private val THREAD_FACTORY = object : ThreadFactory {

            val count = AtomicInteger(1)
            override fun newThread(r: Runnable): Thread {
                return Thread(r, "$TAG#${count.getAndIncrement()}")
            }
        }
        private val THREAD_POOL_EXECUTOR = ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS
                , LinkedBlockingQueue<Runnable>(), THREAD_FACTORY
        )

        // Cache
        private val TAG_KEY_url = R.id.ryg_ch12_image_loader_uri
        private val DISK_CACHE_SIZE = 1024 * 1024 * 50L
        private val IO_BUFFER_SIZE = 8 * 1024
        private val DISK_CACHE_INDEX = 0

        /**
         * build a new instance of ImageLoader
         * @param context
         * @return a new instance of ImageLoader
         */
        fun build(context: Context): ImageLoader {
            return ImageLoader(context)
        }
    }

    private var mMainHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val result = msg.obj as LoaderResult
            val imageView = result.imageView
            val url = imageView.getTag(TAG_KEY_url)
            if (url.equals(result.url)) {
                imageView.setImageBitmap(result.bitmap)
            } else {
                Logger.w(TAG, "set image bitmap,but url has changed, ignored!")
            }
        }
    }
    private var mContext: Context
    private var mMemoryCache: LruCache<String, Bitmap>
    private var mDiskLruCache: DiskLruCache? = null
    private var mIsDiskLruCacheCreated = false

    private constructor(context: Context) {
        mContext = context.applicationContext
        val maxMemory = Runtime.getRuntime().maxMemory() / 1024
        val cacheSize = (maxMemory / 8).toInt()
        mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }
        }
        val diskCacheDir = LoaderUtils.getDiskCacheDir(mContext, "bitmap")
        if (diskCacheDir.exists()) {
            diskCacheDir.mkdirs()
        }
        if (LoaderUtils.getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            Logger.d(TAG, "constructor :: DiskLruCache open")
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE)
                mIsDiskLruCacheCreated = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * load bitmap from memory cache or disk cache or network async, then bind imageView and bitmap.
     * NOTE THAT: should run in UI Thread
     * @param url http url
     * @param imageView bitmap's bind object
     */
    fun bindBitmap(url: String, imageView: ImageView) {
        bindBitmap(url, imageView, 0, 0)
    }

    fun bindBitmap(url: String, imageView: ImageView, reqWidth: Int, reqHeight: Int) {
        imageView.setTag(TAG_KEY_url, url)
        val bitmap = loadBitmapFromMemCache(url)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
            return
        }

        val loadBitmapTask = Runnable {
            val bitmap = loadBitmap(url, reqWidth, reqHeight)
            if (bitmap != null) {
                val result = LoaderResult(imageView, url, bitmap)
                mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget()
            }
        }

        THREAD_POOL_EXECUTOR.execute(loadBitmapTask)
    }

    /**
     * load bitmap from memory cache or disk cache or network.
     * @param url http url
     * @param reqWidth the width ImageView desired
     * @param reqHeight the height ImageView desired
     * @return bitmap, maybe null.
     */
    private fun loadBitmap(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        var bitmap = loadBitmapFromMemCache(url)
        if (bitmap != null) {
            return bitmap
        }

        try {
            bitmap = loadBitmapFromDiskCache(url, reqWidth, reqHeight)
            if (bitmap != null) {
                return bitmap
            }

            bitmap = loadBitmapFromHttp(url, reqWidth, reqHeight)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (bitmap == null && !mIsDiskLruCacheCreated) {
            bitmap = LoaderUtils.downloadBitmapFromUrl(url, IO_BUFFER_SIZE)
        }

        return bitmap
    }


    // Memory
    private fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap)
        }
    }

    private fun getBitmapFromMemCache(key: String): Bitmap? {
        return mMemoryCache.get(key)
    }

    private fun loadBitmapFromMemCache(url: String): Bitmap? {
        return getBitmapFromMemCache(LoaderUtils.hashKeyFormUrl(url))
    }

    // Disk
    private fun loadBitmapFromDiskCache(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Logger.w(TAG, "load bitmap from UI Thread, it's not recommended!");
        }

        if (mDiskLruCache == null) {
            return null
        }

        var bitmap: Bitmap? = null
        val key = LoaderUtils.hashKeyFormUrl(url)
        val snapShot = mDiskLruCache!!.get(key)
        if (snapShot != null) {
            val fileInputStream = snapShot.getInputStream(DISK_CACHE_INDEX) as FileOutputStream
            val fileDescriptor = fileInputStream.fd
            bitmap = ImageResizer.decodeSampledBitmapFromFileDescriptor(fileDescriptor, reqWidth,
                    reqHeight)
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap)
            }
        }

        return bitmap
    }

    private class LoaderResult(var imageView: ImageView, var url: String, var bitmap: Bitmap)

    // Http
    private fun loadBitmapFromHttp(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw RuntimeException("can not visit network from UI Thread.");
        }

        if (mDiskLruCache == null) {
            return null
        }

        val key = LoaderUtils.hashKeyFormUrl(url)
        val editor = mDiskLruCache!!.edit(key)
        if (editor != null) {
            val outputStream = editor.newOutputStream(DISK_CACHE_INDEX)
            if (LoaderUtils.downloadUrlToStream(url, outputStream, IO_BUFFER_SIZE)) {
                editor.commit()
            } else {
                editor.abort()
            }
            mDiskLruCache!!.flush()
        }

        return loadBitmapFromDiskCache(url, reqWidth, reqHeight)
    }
}