package com.halcyonmobile.multiplatformplayground.shared.observer

expect class Observable<T : Any>() {
    var value: T?

    fun observe(observer: Observer<T>)
    fun observe(doOnChange: (T?) -> Unit)
    fun removeObserver(observer: Observer<T>)
}

fun <T : Any> observableOf(value: T) = Observable<T>().also { it.value = value }

fun <T : Any, R : Any> Observable<T>.map(mapper: (T) -> (R)) =
    Observable<R>().also { newObservable ->
        observe { newValue ->
            newValue?.let {
                newObservable.value = mapper(it)
            }
        }
    }