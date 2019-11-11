package com.halcyonmobile.multiplatformplayground.storage

interface Database {
    suspend fun getApplications(): List<Application>
}