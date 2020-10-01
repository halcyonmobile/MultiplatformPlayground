package com.halcyonmobile.multiplatformplayground.api.db

import android.content.Context
import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver

internal actual class DatabaseFactory private actual constructor() {
    private lateinit var context: Context

    actual fun create() = MultiplatformDatabase(
        AndroidSqliteDriver(MultiplatformDatabase.Schema, context, DB_NAME)
    )

    companion object {
        fun getInstance(context: Context) = DatabaseFactory().apply {
            this.context = context
        }
    }
}