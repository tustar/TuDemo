package com.tustar.demo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.demo.data.gen.generateDemos
import com.tustar.demo.data.gen.generateGroups
import com.tustar.demo.data.model.MainItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

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
