package com.tustar.demo.ui.optimize

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Debug
import androidx.annotation.RequiresApi
import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths

object Monitor {
    private const val LIB_NAME = "monitor_agent"
    fun init(application: Application) {
        // 最低支持 Android 8.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val agentPath = createAgentLib(application)
        //加载指定位置的so
        System.load(agentPath)

        //加载jvmti agent
        attachAgent(agentPath, application.classLoader)

        //创建日志存放目录：/sdcard/Android/data/packagename/files/monitor
        val file = application.getExternalFilesDir("")
        val root = File(file, "Monitor")
        root.mkdirs()
        nInit(root.absolutePath)
    }

    private fun attachAgent(agentPath: String?, classLoader: ClassLoader) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Debug.attachJvmtiAgent(agentPath!!, null, classLoader)
            } else {
                val vmDebugClazz = Class.forName("dalvik.system.VMDebug")
                val attachAgentMethod = vmDebugClazz.getMethod("attachAgent", String::class.java)
                attachAgentMethod.isAccessible = true
                attachAgentMethod.invoke(null, agentPath)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createAgentLib(context: Context): String? {
        return try {
            //1、获得so的地址
            val classLoader = context.classLoader
            val findLibrary =
                ClassLoader::class.java.getDeclaredMethod("findLibrary", String::class.java)
            val jvmtiAgentLibPath = findLibrary.invoke(classLoader, LIB_NAME) as String

            //2、创建目录：/data/data/packagename/files/monitor
            val filesDir = context.filesDir
            val jvmtiLibDir = File(filesDir, "monitor")
            if (!jvmtiLibDir.exists()) {
                jvmtiLibDir.mkdirs()
            }
            //3、将so拷贝到上面的目录中
            val agentLibSo = File(jvmtiLibDir, "agent.so")
            if (agentLibSo.exists()) {
                agentLibSo.delete()
            }
            Files.copy(
                Paths.get(File(jvmtiAgentLibPath).absolutePath),
                Paths.get(agentLibSo.absolutePath)
            )
            agentLibSo.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun release() {
        nRelease()
    }

    private external fun nInit(path: String)
    private external fun nRelease()
}