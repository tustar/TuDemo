package com.tustar.ushare.ui.lot

import android.content.Context
import com.tustar.common.util.Logger
import com.tustar.ushare.R
import com.tustar.ushare.data.bean.HttpResult
import com.tustar.ushare.data.bean.Message
import com.tustar.ushare.net.exception.ExceptionHandler
import com.tustar.ushare.net.exception.StatusCode
import com.tustar.ushare.util.CommonDefine

class LotPresenter(var view: LotContract.View) : LotContract.Presenter {

    private var page = 1

    init {
        view.presenter = this
    }

    private val model by lazy {
        LotModel()
    }

    override fun getUsers(context: Context) {
        addSubscription(disposable = model.userList(context, page, CommonDefine.PAGE_SIZE)
                .subscribe({
                    when (it.status) {
                        HttpResult.OK -> {
                            view.updateUsers(it.data)

//                            personList.sortBy ({ it.age })
                        }
                        HttpResult.FAILURE -> {
                            when (it.message) {
                                Message.Unauthorized -> Logger.d("Sign Error")
                                else -> {
                                }
                            }
                        }
                        else -> {

                        }
                    }
                }) {
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