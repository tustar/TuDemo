package com.tustar.retrofit2.net

import com.tustar.retrofit2.data.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {

    // 发送验证码
    @FormUrlEncoded
    @POST("/user/code")
    fun sendCode(@FieldMap params: Map<String, String>): Observable<HttpResult<Any, Any>>

    // 登录
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@FieldMap params: Map<String, String>): Observable<HttpResult<Any, Any>>
}