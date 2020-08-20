package com.halcyonmobile.multiplatformplayground.shared.observer

import com.halcyonmobile.multiplatformplayground.shared.util.log

actual class Observable<T : Any> {
    actual var value: T? = null
        set(value) {
            field = value
            observers.forEach {
                it.onChanged(value)
            }
        }

    private val observers = mutableListOf<Observer<T>>()

    actual fun observe(observer: Observer<T>) {
        observers.add(observer)
    }

    actual fun removeObserver(observer: Observer<T>) {
        observers.remove(observer)
    }

    actual fun observe(doOnChange: (T?) -> Unit) =
        observe(object : Observer<T>() {
            override fun onChanged(value: T?) {
                doOnChange(value)
            }
        })
}