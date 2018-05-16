package com.tustar.common.util

import android.app.Activity

object ActivityCollector {

    @JvmField
    var activities: MutableList<Activity> = ArrayList()

    @JvmStatic
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    @JvmStatic
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    @JvmStatic
    fun finishAll() {
        activities.forEach {
                it.finish()
        }
        activities.clear()
    }
}