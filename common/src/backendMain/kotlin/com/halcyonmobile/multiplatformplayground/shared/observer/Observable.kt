package com.halcyonmobile.multiplatformplayground.shared.observer

actual class Observable<T> actual constructor() {
    actual var value: T? = null

    actual fun observe(observer: Observer<T>) {
    }

    actual fun removeObserver(observer: Observer<T>) {
    }

}