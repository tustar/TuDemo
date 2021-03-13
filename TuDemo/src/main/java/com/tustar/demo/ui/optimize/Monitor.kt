package com.tustar.demo.ui.optimize

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Debug
import androidx.annotation.RequiresApi
import java.io.File
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

object Monitor {
    private const val LIB_NAME = "monitor_agent"

    /**
     * 开启监控
     * @param application
     */
    fun init(application: Application) {
        // 最低支持8.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        // so的地址
        val agentPath = createAgentLib(application)
        System.load(agentPath)

        // 加载jvmti
        attachAgent(agentPath, application.classLoader)

        // 准备一个目录，存放日志
        val file = application.getExternalFilesDir("")
        val root = File("Monitor")
        root.mkdirs()
        native_init(root.absolutePath)
    }

    private fun attachAgent(agentPath: String?, classLoader: ClassLoader) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Debug.attachJvmtiAgent(agentPath!!, null, classLoader)
            } else {
                val vmDebug = Class.forName("dalvik.system.VMDebug")
                val attachAgent = vmDebug.getMethod("attachAgent", String::class.java)
                attachAgent.isAccessible = true
                attachAgent.invoke(null, agentPath)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createAgentLib(context: Context): String? {
        try {
            val classLoader = context.classLoader
            val findLibrary = ClassLoader::class.java.getDeclaredMethod(
                "findLibrary",
                String::class.java
            )

            // so地址
            val jvmtiAgentLibPath = findLibrary.invoke(classLoader, LIB_NAME) as String

            // 把so拷贝到程序私有目录/data/data/packagename/files/monitor/agent.so
            val fileDir = context.filesDir
            val jvmtiLibDir = File(fileDir, "monitor")
            if (!jvmtiLibDir.exists()) {
                jvmtiLibDir.mkdirs()
            }
            val agentLibSo = File(jvmtiLibDir, "agent.so")
            if (agentLibSo.exists()) {
                agentLibSo.delete()
            }
            Files.copy(
                Paths.get(File(jvmtiAgentLibPath).absolutePath),
                Paths.get(agentLibSo.absolutePath), StandardCopyOption.REPLACE_EXISTING
            )
            return agentLibSo.absolutePath
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    external fun native_init(path: String?)

    init {
        System.loadLibrary("xxx")
    }
}