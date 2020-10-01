package com.halcyonmobile.multiplatformplayground.api.db

import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

internal actual class DatabaseFactory private actual constructor() {
    actual fun create() =
        MultiplatformDatabase(NativeSqliteDriver(MultiplatformDatabase.Schema, DB_NAME))

    companion object {
        fun getInstance() = DatabaseFactory()
    }
}
