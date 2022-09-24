package com.tustar.utils

fun <T> Iterable<T>.sql() = joinToString(",", "(", ")") {
    "'$it'"
}