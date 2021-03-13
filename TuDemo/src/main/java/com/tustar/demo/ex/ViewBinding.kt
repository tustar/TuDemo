package com.tustar.demo.base

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.tustar.demo.util.Logger
import kotlin.properties.ReadOnlyProperty

inline fun <reified T : ViewBinding> Fragment.bind(): FragmentBindingDelegate<T> {
    return FragmentBindingDelegate(T::class.java, this)
}

inline fun Lifecycle.donOnDestroy(crossinline destroyed: () -> Unit) {
    addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                destroyed()
                source.lifecycle.removeObserver(this)
            }
        }
    })
}

class FragmentBindingDelegate<T : ViewBinding>(classes: Class<T>, fragment: Fragment) :
    ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    private val bindViewMethod by lazy { classes.getMethod("bind", View::class.java) }

    init {
        fragment.lifecycle.doOnDestroy { release() }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return binding ?: let {
            Logger.e("generate value")
            (bindViewMethod.invoke(null, thisRef.view) as T).also { binding = it }
        }
    }

    private fun release() {
        Logger.e("release binding")

        binding = null
    }
}