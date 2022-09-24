package com.tustar.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: T
    protected abstract val bindingInflate: (LayoutInflater) -> T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initData()
    }

    abstract fun initViews()

    abstract fun initData()
}