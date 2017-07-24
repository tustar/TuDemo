package com.tustar.demo.module.ryg.ch2.socket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.text.TextUtils
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch2.utils.Utils
import kotlinx.android.synthetic.main.activity_ryg_tcpclient.*
import java.io.*
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*


class TCPClientActivity : BaseActivity() {

    companion object {
        private val TAG = TCPClientActivity::class.java.simpleName
        private val MESSAGE_RECEIVE_NEW_MSG = 1
        private val MESSAGE_SOCKET_CONNECTED = 2
    }

    private var mPrintWriter: PrintWriter? = null
    private var mClientSocket: Socket? = null

    private var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_RECEIVE_NEW_MSG -> {
                    ryg_msg_container.text = "${ryg_msg_container.text}${msg.obj}"
                }
                MESSAGE_SOCKET_CONNECTED -> {
                    ryg_send_btn.isEnabled = true
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_tcpclient)
        title = getString(R.string.ryg_ch2_socket)
        ryg_send_btn.setOnClickListener {
            var msg = ryg_msg_et.text.toString()
            if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
                Thread({mPrintWriter!!.println(msg)}).start()
                ryg_msg_et.setText("")
                var time = formatDateTime(System.currentTimeMillis())
                var showedMsg = "Client $time:$msg\n"
                ryg_msg_container.setText("${ryg_msg_container.text}$showedMsg")
            }
        }
        startService(Intent(this, TCPServerService::class.java))
        Thread({ connectTCPServer() }).start()
    }

    private fun formatDateTime(time: Long): String {
        return SimpleDateFormat("(HH:mm:ss)").format(Date(time))
    }

    override fun onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket!!.shutdownInput()
                mClientSocket!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        super.onDestroy()
    }

    private fun connectTCPServer() {
        while (mClientSocket == null) {
            try {
                mClientSocket = Socket("localhost", 8688)
                mPrintWriter = PrintWriter(BufferedWriter(OutputStreamWriter(
                        mClientSocket!!.getOutputStream())), true)
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED)
            } catch (e: IOException) {
                SystemClock.sleep(1000)
            }
        }

        try {
            // 接收服务器端的消息
            var br = BufferedReader(InputStreamReader(mClientSocket!!.getInputStream()))
            while (!isFinishing) {
                val msg = br.readLine()
                if (msg != null) {
                    val time = formatDateTime(System.currentTimeMillis())
                    val showedMsg = "Server $time:$msg\n"
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg)
                            .sendToTarget()
                }
            }
            Utils.close(mPrintWriter)
            Utils.close(br)
            mClientSocket!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
