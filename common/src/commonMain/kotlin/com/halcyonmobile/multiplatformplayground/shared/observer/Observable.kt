package com.halcyonmobile.multiplatformplayground.shared.observer

expect class Observable<T : Any>() {
    var value: T?

    fun observe(observer: Observer<T>)
    fun removeObserver(observer: Observer<T>)
}

fun <T : Any> observableOf(value: T) = Observable<T>().also { it.value = value }

fun <T : Any> Observable<T>.observe(doOnChange: (T?) -> Unit) =
    observe(object : Observer<T>() {
        override fun onChanged(value: T?) {
            doOnChange(value)
        }
    })

fun <T : Any, R : Any> Observable<T>.map(mapper: (T) -> (R)) =
    Observable<R>().also { newObservable ->
        observe { newValue ->
            newValue?.let {
                newObservable.value = mapper(it)
            }
        }
    }