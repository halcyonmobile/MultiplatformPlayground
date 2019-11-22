package com.halcyonmobile.multiplatformplayground.shared.util.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf

fun <T> Flow<T>.merge(vararg other: Flow<T>): Flow<T> = flowOf(this, *other).flattenMerge()
