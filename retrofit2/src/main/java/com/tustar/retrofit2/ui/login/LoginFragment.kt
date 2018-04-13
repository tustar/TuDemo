package com.tustar.retrofit2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.tustar.retrofit2.R
import com.tustar.retrofit2.base.BaseFragment

class LoginFragment : BaseFragment(), LoginContract.View {


    private lateinit var loginNameText:EditText
    private lateinit var loginPasswordText:EditText
    private lateinit var loginSignBtn: Button
    private lateinit var loginProgressBar:ProgressBar



    override lateinit var presenter: LoginContract.Presenter

    override var isActive: Boolean = false
        get() = isAdded

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        with(root) {
            loginNameText = findViewById(R.id.login_name_text)
            loginNameText = findViewById(R.id.login_pwd_text)
            loginSignBtn = findViewById(R.id.login_sign_in_btn)
        }
        loginSignBtn.setOnClickListener {
            presenter.login()
        }
        return view
    }

    override fun getName(): String {
        return loginNameText.text.toString()
    }

    override fun getPassword(): String {
        return loginPasswordText.text.toString()
    }

    override fun setLoadingIndicator(visible: Boolean) {
        loginProgressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }
}