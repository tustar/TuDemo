package com.tustar.ushare.ui.lot

class LotPresenter(var view: LotContract.View) : LotContract.Presenter {

    init {
        view.presenter = this
    }

    private val model by lazy {
        LotModel()
    }
}