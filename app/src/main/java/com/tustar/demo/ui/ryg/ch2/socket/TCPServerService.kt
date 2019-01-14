package com.tustar.demo.ui.ryg.ch2.socket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Created by tustar on 17-7-12.
 */
class TCPServerService : Service() {

    private var mIsServiceDestroyed = AtomicBoolean(false)
    private val mDefinedMessages = arrayOf("你好啊，哈哈",
            "请问你叫什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。")

    override fun onCreate() {
        Thread(TcpServer()).start()
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        mIsServiceDestroyed.set(true)
        super.onDestroy()
    }

    inner class TcpServer : Runnable {
        override fun run() {
            try {

                var serverSocket = ServerSocket(8688)
                while (!mIsServiceDestroyed.get()) {
                    var client = serverSocket.accept()
                    Thread({
                        responseClient(client)
                    }).start()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun responseClient(client: Socket) {
        // 用于接收客户端消息
        BufferedReader(InputStreamReader(
                client.getInputStream())).use { kin ->
            // 用于向客户端发送消息
            PrintWriter(BufferedWriter(
                    OutputStreamWriter(client.getOutputStream())), true).use { out ->
                out.println("欢迎来到聊天室！")
                while (!mIsServiceDestroyed.get()) {
                    kin.readLine() ?: break
                    val i = Random().nextInt(mDefinedMessages.size)
                    val msg = mDefinedMessages[i]
                    out.println(msg)
                }
                client.close()
            }
        }
    }
}