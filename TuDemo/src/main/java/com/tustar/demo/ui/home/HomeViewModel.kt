package com.tustar.demo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.demo.data.gen.generateDemos
import com.tustar.demo.data.model.GroupItem
import com.tustar.demo.data.model.MainItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val demos get() = _demos
    private val _demos = MutableLiveData<LinkedList<MainItem>>()
    fun findDemos() = viewModelScope.launch(Dispatchers.IO) {
        val demos = LinkedList<MainItem>()
        val gens = generateDemos()
        val map = gens.groupBy { it.group }
        map.forEach { (group, items) ->
            demos.add(GroupItem(group))
            demos.addAll(items)
        }
        _demos.postValue(demos)
    }

    fun createDemos() = generateDemos().groupBy { it.group }
}
