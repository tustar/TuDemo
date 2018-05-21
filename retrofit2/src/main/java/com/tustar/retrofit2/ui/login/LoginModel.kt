package com.tustar.retrofit2.ui.login

import android.content.Context
import com.tustar.common.util.DeviceUtils
import com.tustar.retrofit2.data.bean.Code
import com.tustar.retrofit2.data.bean.HttpResult
import com.tustar.retrofit2.net.RetrofitManager
import com.tustar.retrofit2.rx.scheduler.SchedulerUtils
import com.tustar.retrofit2.util.NetUtils
import io.reactivex.Observable


class LoginModel {

    /**
     * 获取验证码
     */
    fun code(context: Context, mobile: String): Observable<HttpResult<Code, Any>> {
        var params = mutableMapOf<String, String>()
        params["mobile"] = mobile
        params["deviceId"] = DeviceUtils.getDeviceId(context) ?: ""
        params = NetUtils.getSignedParams(context, params)

        return RetrofitManager.service
                .sendCode(params).compose(SchedulerUtils.ioToMain())
    }

    /**
     * 登录
     */
    fun login(context: Context, mobile: String, code: String): Observable<HttpResult<Any, Any>> {
        var params = mutableMapOf<String, String>()
        params["mobile"] = mobile
        params["code"] = code
        params = NetUtils.getSignedParams(context, params)

        return RetrofitManager.service
                .login(params).compose(SchedulerUtils.ioToMain())
    }
}
