package com.tustar.ktx

fun <T> Iterable<T>.sql() = joinToString(",", "(", ")") {
    "'$it'"
}