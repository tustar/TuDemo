package com.tustar.ushare.ui.mine

import com.tustar.ushare.ui.topic.MineModel


class MinePresenter(var view: MineContract.View) : MineContract.Presenter {

    init {
        view.presenter = this
    }

    private val model by lazy {
        MineModel()
    }
}