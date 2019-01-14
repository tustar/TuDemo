package com.tustar.demo.ui.ryg.ch2.utils

import android.os.Environment

/**
 * Created by tustar on 17-6-26.
 */
object MyConstants {
    var CHAPTER_2_PATH = Environment.getExternalStorageDirectory()
            .path + "/TuDemo/chapter_2/"

    val CACHE_FILE_PATH = CHAPTER_2_PATH + "usercache"

    val MSG_FROM_CLIENT = 0
    val MSG_FROM_SERVICE = 1

    val REMOTE_ACTION = "com.ryg.chapter.action_REMOTE"
    val EXTRA_REMOTE_VIEWS = "extra_remoteViews"
}