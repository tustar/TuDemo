package com.tustar.retrofit2.ui.login

import android.content.Context
import com.tustar.common.util.Logger
import com.tustar.common.util.MobileUtils
import com.tustar.retrofit2.R
import com.tustar.retrofit2.data.bean.HttpResult
import com.tustar.retrofit2.data.bean.MsgCode
import com.tustar.retrofit2.net.exception.ExceptionHandler
import com.tustar.retrofit2.net.exception.StatusCode
import com.tustar.retrofit2.util.CodeUtils

class LoginPresenter(var view: LoginContract.View) : LoginContract.Presenter {

    init {
        view.presenter = this
    }

    private val model by lazy {
        LoginModel()
    }

    override fun login(mobile: String, code: String) {
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
        addSubscription(disposable = model.login(mobile, code).subscribe({
            view.setSubmitEnable(true)
            when (it.code) {
                HttpResult.OK -> {
                    val user = it.data
                }
                HttpResult.FAILURE -> {
                    when (it.msg) {
                        MsgCode.UNVALID_MSG_CODE -> view.showToast(R.string.login_unvalid_msg_code)
                        MsgCode.INSERT_FAIL -> view.showToast(R.string.login_insert_fail)
                        MsgCode.MOBILE_FORMAT_ERROR -> view.showToast(R.string.login_mobile_format_error)
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
            when (it.code) {
                HttpResult.OK -> {
                    view.showToast(R.string.login_code_get_success)
                }
                HttpResult.FAILURE -> {
                    when (it.msg) {
                        MsgCode.SEND_MSG_OVERDUE -> view.showToast(R.string.login_send_msg_overdue)
                        MsgCode.SEND_MSG_ERROR -> view.showToast(R.string.login_send_msg_error)
                        MsgCode.MOBILE_FORMAT_ERROR -> view.showToast(R.string.login_mobile_format_error)
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