package com.tustar.demo.ui.ryg.ch12.loader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.os.StatFs
import java.io.File
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * Created by tustar on 17-8-31.
 */
object LoaderUtils {
    fun getDiskCacheDir(context: Context, uniqueName: String): File {
        val externalStorageAvailable = Environment.getExternalStorageState() ==
                Environment.MEDIA_MOUNTED
        var cachePath = if (externalStorageAvailable) {
            context.externalCacheDir?.path
        } else {
            context.cacheDir.path
        }

        return File(cachePath + File.separator + uniqueName)
    }

    fun getUsableSpace(path: File): Long {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.usableSpace
        }

        val stats = StatFs(path.path)
        return stats.availableBytes
    }

    fun bytesToHexString(bytes: ByteArray): String {
        var sb = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }

    fun hashKeyFormUrl(url: String): String {
        var cacheKey: String
        try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update(url.toByteArray())
            cacheKey = bytesToHexString(digest.digest())
        } catch (e: NoSuchAlgorithmException) {
            cacheKey = url.hashCode().toString()
        }
        return cacheKey
    }

    fun downloadBitmapFromUrl(url: String, bufferSize: Int): Bitmap? {
        val connection = URL(url).openConnection() as HttpURLConnection
        var bitmap: Bitmap? = null
        try {
            connection.inputStream.buffered(bufferSize).use { `in` ->
                bitmap = BitmapFactory.decodeStream(`in`)
            }
        } finally {
            connection.disconnect()
        }
        return bitmap
    }

    fun downloadUrlToStream(url: String, output: OutputStream, bufferSize: Int): Boolean {
        val connection = URL(url).openConnection() as HttpURLConnection
        var result = false
        try {
            connection.inputStream.buffered(bufferSize).use { `in` ->
                output.buffered(bufferSize).use { `out` ->
                    {
                        var b = `in`.read()
                        while(b != -1) {
                            //
                            `out`.write(b)
                            b = `in`.read()
                        }
                        result = true
                    }
                }
            }
        } finally {
            connection.disconnect()
        }

        return result
    }
}