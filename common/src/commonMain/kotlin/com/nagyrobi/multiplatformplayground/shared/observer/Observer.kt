package com.nagyrobi.multiplatformplayground.shared.observer

interface Observer<T> {
    fun onChanged(value: T)
}