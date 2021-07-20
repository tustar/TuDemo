package com.tustar.demo.ui.custom

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.Nullable


object RecorderDao {

    private val PROJECTION = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.SIZE,
        MediaStore.Audio.Media.DATE_MODIFIED
    )

    private val AUDIO_URI: Uri = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } else {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    @Nullable
    fun create(context: Context, displayName: String, mimeType: String): Uri? {
        //
        val resolver: ContentResolver = context.contentResolver
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
            put(MediaStore.Audio.Media.TITLE, displayName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.Audio.Media.IS_MUSIC, "0")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Audio.Media.IS_PENDING, 1)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_MUSIC}demo")
            }
        }

        return resolver.insert(AUDIO_URI, values)
    }

    fun updatePending(context: Context, uri: Uri): Boolean {
        val resolver = context.contentResolver
        val values = ContentValues()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Audio.Media.IS_PENDING, 0)
        }
        val result = resolver.update(uri, values, null, null)
        return result == 1
    }

    fun delete(context: Context, uri: Uri) {
        context.contentResolver.delete(uri, null, null)
    }
}