package com.tustar.demo.module.ryg.ch11

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import com.tustar.demo.R
import com.tustar.demo.module.ryg.base.BaseRygActivity
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class RygCh11Activity : BaseRygActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch11_title)

        scheduleThreads()
    }

    private fun scheduleThreads() {
        runAsyncTask()
        runIntentService()
        runThreadPool()
    }

    private fun runAsyncTask() {
        try {
            DownloadFilesTask().execute(URL("http://www.baidu.com"),
                    URL("http://www.renyugang.cn"))
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    private fun runIntentService() {
        val service = Intent(this, LocalIntentService::class.java)
        service.putExtra("task_action", "com.ryg.action.TASK1")
        startService(service)
        service.putExtra("task_action", "com.ryg.action.TASK2")
        startService(service)
        service.putExtra("task_action", "com.ryg.action.TASK3")
        startService(service)
    }

    private fun runThreadPool() {
        val command = Runnable { SystemClock.sleep(2000) }

        val fixedThreadPool = Executors.newFixedThreadPool(4)
        fixedThreadPool.execute(command)

        val cachedThreadPool = Executors.newCachedThreadPool()
        cachedThreadPool.execute(command)

        val scheduledThreadPool = Executors.newScheduledThreadPool(4)
        // 2000ms后执行command
        scheduledThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS)
        // 延迟10ms后，每隔1000ms执行一次command
        scheduledThreadPool.scheduleAtFixedRate(command, 10, 1000, TimeUnit.MILLISECONDS)

        val singleThreadExecutor = Executors.newSingleThreadExecutor()
        singleThreadExecutor.execute(command)
    }

    private inner class DownloadFilesTask : AsyncTask<URL, Int, Long>() {

        override fun doInBackground(vararg urls: URL): Long {
            val count = urls.size
            val totalSize = 0L
            for (i in 0 until count - 1) {
                publishProgress((i / count.toFloat() * 100).toInt())
                if (isCancelled) {
                    break
                }
            }

            return totalSize
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
        }
    }
}
