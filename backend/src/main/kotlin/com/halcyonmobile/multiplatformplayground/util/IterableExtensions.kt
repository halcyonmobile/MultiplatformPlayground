package com.halcyonmobile.multiplatformplayground.util

fun <T> Iterable<T>.getPage(page: Int, perPage: Int) =
    if (page < 0) throw IllegalArgumentException("pageOffset shouldn't be negative ")
    else with(toList()) {
        subList(
            (page * perPage).coerceAtMost(size),
            ((page + 1) * perPage).coerceAtMost(size)
        )
    }