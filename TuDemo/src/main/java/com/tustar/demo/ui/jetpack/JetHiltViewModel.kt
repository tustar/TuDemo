package com.tustar.demo.ui.jetpack

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JetHiltViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return JetHiltViewModel(context) as T
    }
}

class JetHiltViewModel(context: Context) : ViewModel() {

}