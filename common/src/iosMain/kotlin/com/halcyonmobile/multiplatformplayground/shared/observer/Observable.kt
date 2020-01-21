package com.halcyonmobile.multiplatformplayground.shared.observer

actual class Observable<T> {
    actual var value: T? = null
        set(value) {
            field = value
            observers.forEach { it.onChanged(value) }
        }

    private val observers = mutableListOf<Observer<T>>()


    actual fun observe(observer: Observer<T>) {
        observers.add(observer)
    }

    actual fun removeObserver(observer: Observer<T>) {
        observers.remove(observer)
    }
}