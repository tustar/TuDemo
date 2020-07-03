package com.tustar.ex

fun <T> Iterable<T>.sql() = joinToString(",", "(", ")") {
    "'$it'"
}