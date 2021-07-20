package com.tustar.demo.permisson

interface IRequest {
    fun request()
    fun isGranted(): Boolean
}