package com.tustar.ushare.ui.topic


class TopicPresenter(var view: TopicContract.View) : TopicContract.Presenter {

    init {
        view.presenter = this
    }

    private val model by lazy {
        MineModel()
    }
}