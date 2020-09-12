package com.tustar.demo.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.demo.data.gen.generateDemos
import com.tustar.demo.data.gen.generateGroups
import com.tustar.demo.data.model.MainItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel @ViewModelInject constructor() : ViewModel() {

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
