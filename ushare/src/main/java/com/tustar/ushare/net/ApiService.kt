package com.tustar.ushare.net

import com.tustar.ushare.data.bean.Code
import com.tustar.ushare.data.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {

    // 发送验证码
    @FormUrlEncoded
    @POST("v1/user/code")
    fun sendCode(@FieldMap params: Map<String, String>): Observable<HttpResult<Code, Any>>

    // 登录
    @FormUrlEncoded
    @POST("v1/user/login")
    fun login(@FieldMap params: Map<String, String>): Observable<HttpResult<Any, Any>>
}