package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            MultiplatformDatabase.Schema,
            get(),
            "multiplatform.db"
        )
    }
}