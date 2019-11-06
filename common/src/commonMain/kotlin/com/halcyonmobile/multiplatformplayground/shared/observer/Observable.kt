package com.halcyonmobile.multiplatformplayground.shared.observer

expect class Observable<T>() {
    var value: T?

    fun observe(observer: Observer<T>)
    fun removeObserver(observer: Observer<T>)
}

fun <T> Observable<T>.observe(doOnChange: (T?) -> Unit) =
    observe(object : Observer<T> {
        override fun onChanged(value: T?) {
            doOnChange(value)
        }
    })

fun <T, R> Observable<T>.map(mapper: (T) -> (R)) =
    Observable<R>().also { newObservable ->
        observe { newValue ->
            newValue?.let {
                newObservable.value = mapper(it)
            }
        }
    }