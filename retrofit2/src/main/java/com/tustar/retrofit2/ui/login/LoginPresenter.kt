package com.tustar.retrofit2.ui.login


class LoginPresenter(val loginView: LoginContract.View) : LoginContract.Presenter {

    init {
        loginView.presenter = this
    }

    override fun login(name: String, password: String) {

    }
}