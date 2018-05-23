package com.tustar.ushare.ui.login

import android.content.Context
import com.tustar.common.util.Logger
import com.tustar.common.util.MobileUtils
import com.tustar.ushare.R
import com.tustar.ushare.data.bean.HttpResult
import com.tustar.ushare.data.bean.Message
import com.tustar.ushare.net.exception.ExceptionHandler
import com.tustar.ushare.net.exception.StatusCode
import com.tustar.ushare.util.CodeUtils

class LoginPresenter(var view: LoginContract.View) : LoginContract.Presenter {

    init {
        view.presenter = this
    }

    private val model by lazy {
        LoginModel()
    }

    override fun login(context: Context, mobile: String, code: String) {
        Logger.d("mobile = $mobile, code = $code")
        if (!MobileUtils.isMobileOk(mobile)) {
            view.showToast(R.string.login_phone_err)
            return
        }

        if (!CodeUtils.isCodeOk(code)) {
            view.showToast(R.string.login_code_err)
            return
        }

        view.setSubmitEnable(false)
        addSubscription(disposable = model.login(context, mobile, code).subscribe({
            view.setSubmitEnable(true)
            when (it.status) {
                HttpResult.OK -> {
                    val user = it.data
                    view.toMainUI()
                }
                HttpResult.FAILURE -> {
                    when (it.msg) {
                        Message.Unauthorized -> Logger.d("Sign Error")
                        else -> view.showToast(R.string.login_submit_err)
                    }
                }
                else -> {
                    // 更多情况
                }
            }
        }) {
            //
            view.setSubmitEnable(true)
            val code = ExceptionHandler.handleException(it)
            when (code) {
                StatusCode.SOCKET_TIMEOUT_ERROR -> view.showToast(R.string.socket_timeout_error)
                StatusCode.CONNECT_ERROR -> view.showToast(R.string.connect_error)
                StatusCode.UNKNOWN_HOST_ERROR -> view.showToast(R.string.unkown_host_error)
                StatusCode.SERVER_ERROR -> view.showToast(R.string.server_err)
                else -> view.showToast(R.string.login_submit_err)
            }
        })
    }

    override fun sendCode(context: Context, mobile: String) {

        if (!MobileUtils.isMobileOk(mobile)) {
            view.showToast(R.string.login_phone_err)
            return
        }

        view.setCodeGetEnable(false)
        addSubscription(disposable = model.code(context, mobile).subscribe({
            view.startCodeTimer()
            when (it.status) {
                HttpResult.OK -> {
                    view.showToast(R.string.login_code_get_success)
                    view.showVerificationCode(it.data.v_code)
                }
                HttpResult.FAILURE -> {
                    when (it.msg) {
                        Message.Unauthorized -> Logger.d("Sign Error")
                        else -> {
                        }
                    }
                }
                else -> {

                }
            }
        }) {
            view.setCodeGetEnable(true)
            val code = ExceptionHandler.handleException(it)
            when (code) {
                StatusCode.SOCKET_TIMEOUT_ERROR -> view.showToast(R.string.socket_timeout_error)
                StatusCode.CONNECT_ERROR -> view.showToast(R.string.connect_error)
                StatusCode.UNKNOWN_HOST_ERROR -> view.showToast(R.string.unkown_host_error)
                StatusCode.SERVER_ERROR -> view.showToast(R.string.server_err)
                else -> view.showToast(R.string.login_code_get_err)
            }
        })
    }
}