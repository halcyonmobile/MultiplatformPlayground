package com.halcyonmobile.multiplatformplayground.api.db

import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase

internal expect class DatabaseFactory private constructor() {
    fun create(): MultiplatformDatabase
}

internal const val DB_NAME = "multiplatform.db"
