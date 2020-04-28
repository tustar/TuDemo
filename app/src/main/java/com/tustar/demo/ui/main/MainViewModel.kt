package com.tustar.demo.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tustar.demo.data.DemoDatabase
import com.tustar.demo.data.DemoRepository
import com.tustar.demo.data.GroupRepository
import com.tustar.demo.data.MainItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}

class MainViewModel(context: Context) : ViewModel() {

    private val database = DemoDatabase.getInstance(context)
    private val groupRepository = GroupRepository.getInstance(database.groupDao())
    private val demoRepository = DemoRepository.getInstance(database.demoDao())

    val demos get() = _demos
    private val _demos = MutableLiveData<LinkedList<MainItem>>()
    fun findDemos() = viewModelScope.launch(Dispatchers.IO) {
        val items = LinkedList<MainItem>()
        val groups = groupRepository.getGroups()
        groups.forEach { group ->
            items.add(group)
            val children = demoRepository.getDemosByGroupId(group.id)
            items.addAll(children)
        }
        _demos.postValue(items)
    }
}
