package com.halcyonmobile.multiplatformplayground.shared.observer

// Can't use Interface because of Obj-c generics limitations
abstract class Observer<T : Any> {
    abstract fun onChanged(value: T?)
}