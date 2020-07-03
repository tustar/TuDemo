package com.tustar.util

object ActivityCollector {

    @JvmField
    var activities: MutableList<androidx.appcompat.app.AppCompatActivity> = ArrayList()

    @JvmStatic
    fun addActivity(activity: androidx.appcompat.app.AppCompatActivity) {
        activities.add(activity)
    }

    @JvmStatic
    fun removeActivity(activity: androidx.appcompat.app.AppCompatActivity) {
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