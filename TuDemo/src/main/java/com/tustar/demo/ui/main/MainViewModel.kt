package com.tustar.demo.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tustar.demo.data.MainItem
import com.tustar.demo.data.generateDemos
import com.tustar.demo.data.generateGroups
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}

class MainViewModel(context: Context) : ViewModel() {

    val demos get() = _demos
    private val _demos = MutableLiveData<LinkedList<MainItem>>()
    fun findDemos() = viewModelScope.launch(Dispatchers.IO) {
        val items = LinkedList<MainItem>()
        items.addAll(generateGroups())
        items.addAll(generateDemos())
        _demos.postValue(items)
    }
}
