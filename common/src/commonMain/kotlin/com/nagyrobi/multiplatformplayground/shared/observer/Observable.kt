package com.nagyrobi.multiplatformplayground.shared.observer

expect class Observable<T> {

    fun set(value: T)
    fun get(): T
    fun observe(observer: Observer<T>)
    fun removeObserver(observer: Observer<T>)
}