package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(MultiplatformDatabase.Schema, "multiplatform.db") }
}