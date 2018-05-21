package com.tustar.retrofit2.ui.login

import android.content.Context
import com.tustar.retrofit2.base.BasePresenter
import com.tustar.retrofit2.base.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun showToast(resId: Int)
        fun showVerificationCode(vCode: String)
        fun setSubmitEnable(enable: Boolean)
        fun setCodeGetEnable(enable: Boolean)
        fun startCodeTimer()
        fun toMainUI()
    }

    interface Presenter : BasePresenter {
        fun login(context: Context, mobile: String, code: String)
        fun sendCode(context: Context, mobile: String)
    }
}