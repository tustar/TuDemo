package com.tustar.demo

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process
import com.tustar.demo.util.Logger
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class CrashHandler : Thread.UncaughtExceptionHandler {

    companion object {
        private val TAG = "CrashHandler"
        private val CRASH_FILE_NAME = "com.tu.demo_crash"
        private val CRASH_FILE_PATH = Environment.getExternalStorageDirectory().path + "/log/"
        //log文件的后缀名
        private val CRASH_FILE_NAME_SUFFIX = ".log"
    }

    //系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
    private var mDefaultCrashHandler: Thread.UncaughtExceptionHandler? = null

    private var mContext: Context? = null

    //这里主要完成初始化工作
    fun init(context: Context) {
        //获取系统默认的异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
        //获取Context，方便内部使用
        mContext = context
        //可以在初始化后在异步线程中上报上次保存的Crash信息
    }

    /**
     * 这个是最关键的方法，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        try {
            //导出异常信息到SD卡中
            dumpExceptionToSDCard(ex)
            //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //打印出当前调用栈信息
        ex.printStackTrace()

        //如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler!!.uncaughtException(thread, ex)
        } else {
            Process.killProcess(Process.myPid())
        }

    }

    @Throws(IOException::class)
    private fun dumpExceptionToSDCard(ex: Throwable) {
        //如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Logger.e(TAG, "sdcard unmounted,skip dump exception")
            return
        }
        val dir = File(CRASH_FILE_PATH)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val current = System.currentTimeMillis()
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(current))
        //以当前时间创建log文件
        val file = File(CRASH_FILE_PATH + CRASH_FILE_NAME + "_" + time + CRASH_FILE_NAME_SUFFIX)

        try {
            val pw = PrintWriter(BufferedWriter(FileWriter(file)))
            //导出发生异常的时间
            pw.println(time)
            //获取手机信息
            dumpPhoneInfo(pw)

            pw.println()
            //导出异常的调用栈信息
            ex.printStackTrace(pw)

            pw.close()
        } catch (e: Exception) {
            Logger.e(TAG, "dump crash info failed")
        }

    }

    @Throws(PackageManager.NameNotFoundException::class)
    private fun dumpPhoneInfo(pw: PrintWriter) {
        val pm = mContext!!.getPackageManager()
        val pi = pm.getPackageInfo(mContext!!.getPackageName(), PackageManager.GET_ACTIVITIES)
        pw.print("App VersionName: ")
        pw.println(pi.versionName)
        pw.print("App VersionCode: ")
        pw.println(pi.versionCode)

        //android版本号
        pw.print("OS Version: ")
        pw.print(Build.VERSION.RELEASE)
        pw.print("_")
        pw.println(Build.VERSION.SDK_INT)

        //手机制造商
        pw.print("Vendor: ")
        pw.println(Build.MANUFACTURER)

        //手机型号
        pw.print("Model: ")
        pw.println(Build.MODEL)

        //cpu架构
        pw.print("CPU ABI: ")
        pw.println(Build.CPU_ABI)
    }
}