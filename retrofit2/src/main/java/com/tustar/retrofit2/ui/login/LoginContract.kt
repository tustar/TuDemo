package com.tustar.retrofit2.ui.login

import com.example.android.architecture.blueprints.todoapp.BaseView
import com.tustar.retrofit2.base.BasePresenter

interface LoginContract {

    interface View : BaseView<Presenter> {
        var isActive: Boolean
        fun getName(): String
        fun getPassword(): String
        fun setLoadingIndicator(visible: Boolean)
    }

    interface Presenter : BasePresenter {
        fun login()
    }
}