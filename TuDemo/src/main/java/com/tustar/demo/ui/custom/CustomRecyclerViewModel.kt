package com.tustar.demo.ui.custom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tustar.demo.ui.main.MainViewModel

class CustomRecyclerViewModelFactory(private val context: Context)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CustomRecyclerViewModel(context) as T
    }
}

class CustomRecyclerViewModel(context: Context) : ViewModel() {
    // TODO: Implement the ViewModel
}