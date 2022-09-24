package com.tustar.ktstudy

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class CoroutineName1(private val name: String) :
    AbstractCoroutineContextElement(CoroutineName1) {

    companion object Key : CoroutineContext.Key<CoroutineName1>

    override fun toString(): String = "CoroutineName($name)"
}

class CoroutineName2(private val name: String) :
    AbstractCoroutineContextElement(CoroutineName2) {

    companion object Key : CoroutineContext.Key<CoroutineName2>

    override fun toString(): String = "CoroutineName($name)"
}

class CoroutineName3(private val name: String) :
    AbstractCoroutineContextElement(CoroutineName3) {

    companion object Key : CoroutineContext.Key<CoroutineName3>

    override fun toString(): String = "CoroutineName($name)"
}

fun main() {
    val element1 = CoroutineName1("tu")
    val element2 = CoroutineName2("xing")
    val element3 = CoroutineName3("ping")
    val newElement = element1 + element2 + element3
    Logger.d("$newElement")
    val minusKey  = newElement.minusKey(CoroutineName1)
    Logger.d("$minusKey")
    Logger.d("$newElement")
}