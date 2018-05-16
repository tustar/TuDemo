package com.tustar.retrofit2.util

import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


object StreamUtils {

    /**
     * 从 InputStream 读取全部字符串
     *
     * @param inputStrem
     * 输入流
     * @return
     * @throws IOException
     */
    @JvmStatic
    @Throws(IOException::class)
    fun readAll(inputStrem: InputStream): ByteArray? {
        val byteOutputStream = ByteArrayOutputStream()
        val bufferedInputStream = BufferedInputStream(inputStrem)
        while (true) {
            val tempBytes = ByteArray(1024)
            val readSize = bufferedInputStream.read(tempBytes)
            if (readSize > 0) {
                byteOutputStream.write(tempBytes, 0, readSize)
            } else if (readSize == -1 || readSize == 65535) {
                break
            }
        }
        bufferedInputStream.close()
        return if (byteOutputStream.size() === 0) null else byteOutputStream.toByteArray()
    }
}