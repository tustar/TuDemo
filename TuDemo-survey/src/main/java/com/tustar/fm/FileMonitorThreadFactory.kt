package com.tustar.fm

import java.util.concurrent.ThreadFactory

/**
 * Created by tustar on 17-7-17.
 */
class FileMonitorThreadFactory : ThreadFactory {

    private var name: String

    constructor(name: String) {
        this.name = name
    }

    override fun newThread(r: Runnable?): Thread {
        var thread = Thread(r)
        thread.name = this.name
        if (thread.isDaemon) {
            thread.isDaemon = false
        }
        if (thread.priority != 5) {
            thread.priority = 5
        }
        return thread
    }
}