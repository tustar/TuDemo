package com.tustar.demo.ui.systemWidget

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MotionLayoutViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MotionLayoutViewModel(context) as T
    }
}

class MotionLayoutViewModel(context: Context) : ViewModel() {

}