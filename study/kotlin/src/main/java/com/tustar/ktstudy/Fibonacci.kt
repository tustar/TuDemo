package com.tustar.ktstudy

fun main() {
    val fibonacci = sequence {
        yield(1L)
        var cur = 1
        var next = 1
        while (true){
            yield(next)
            val tmp = cur + next
            cur = next
            next = tmp
        }
    }
    fibonacci.take(5).forEach(Logger::d)
}