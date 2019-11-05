package com.halcyonmobile.multiplatformplayground.shared.observer

interface Observer<T> {
    fun onChanged(value: T?)
}