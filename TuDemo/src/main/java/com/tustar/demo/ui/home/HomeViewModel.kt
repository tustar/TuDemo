package com.tustar.demo.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tustar.demo.data.model.MainItem
import com.tustar.demo.data.gen.generateDemos
import com.tustar.demo.data.gen.generateGroups
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(context) as T
    }
}

class HomeViewModel(context: Context) : ViewModel() {

    val demos get() = _demos
    private val _demos = MutableLiveData<LinkedList<MainItem>>()
    fun findDemos() = viewModelScope.launch(Dispatchers.IO) {
        val items = LinkedList<MainItem>()
        val group = generateGroups()
        val demos = generateDemos()
        val demoMap = demos.groupBy { it.groupId }
        group.forEach {
            items.add(it)
            items.addAll(demoMap[it.id] ?: error("Wrong key"))
        }
        _demos.postValue(items)
    }
}
