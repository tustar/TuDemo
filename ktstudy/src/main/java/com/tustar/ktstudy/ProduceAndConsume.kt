package com.tustar.ktstudy

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.broadcast


@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {

//    val receiveChannel = produce {
//        var i = 0
//        while (true) {
//            Logger.d("生产者生产了:$i")
//            for (i in 1..5) {
//                delay(200)
//                Logger.d("生产者生产了:$i")
//                send(i)
//            }
//            close()
//        }
//    }
//
//    val consumer = launch {
//        for (element in receiveChannel) {
//            Logger.d("消费者消费了:$element")
//            delay(2000)
//        }
//    }
//    consumer.join()

    val channel = Channel<Int>()
    val broadcast = channel.broadcast(3)

    val producer = GlobalScope.launch {
        List(5) {
            broadcast.send(it)
            Logger.d("send $it")
        }
        channel.close()
    }

    List(3) { index ->
        GlobalScope.launch {
            val receiveChannel = broadcast.openSubscription()
            for (element in receiveChannel) {
                Logger.d("[$index] receive: $element")
                delay(1000)
            }
        }
    }.forEach { it.join() }

    producer.join()

}