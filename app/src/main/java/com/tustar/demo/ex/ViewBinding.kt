package com.tustar.demo.ex

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.tustar.demo.util.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Activity.inflate() = lazy {
    inflateBinding<T>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified T : ViewBinding> Dialog.inflate() = lazy {
    inflateBinding<T>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified T : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    T::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as T

inline fun <reified T : ViewBinding> Fragment.bind() =
    FragmentBindingDelegate(T::class.java)

class FragmentBindingDelegate<T : ViewBinding>(
    private val clazz: Class<T>
) : ReadOnlyProperty<Fragment, T> {

    private var _binding: T? = null
    private val binding: T get() = _binding!!

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyView() {
                _binding = null
                lifecycle.removeObserver(this)
            }
        })
        _binding = clazz.getMethod("bind", View::class.java)
            .invoke(null, thisRef.requireView()) as T

        return binding
    }
}

