package com.tustar.demo

import org.junit.Test

class ThreadLocalTest {

    @Test
    fun test() {
        val threadLocal = object : ThreadLocal<String>() {
            override fun initialValue(): String? {
//                return super.initialValue()
                return "tu"
            }
        }
        println("${Thread.currentThread().name}: ${threadLocal.get()}")
        Thread {
            println("${Thread.currentThread().name}: ${threadLocal.get()}")

            threadLocal.set("xingping")
            println("${Thread.currentThread().name}: ${threadLocal.get()}")

            // 避免大量无意思的内存占用
            threadLocal.remove()
        }.start()

        Thread {
            println("${Thread.currentThread().name}: ${threadLocal.get()}")

            threadLocal.set("xingping")
            println("${Thread.currentThread().name}: ${threadLocal.get()}")

            // 避免大量无意思的内存占用
            threadLocal.remove()
        }.start()
    }
}