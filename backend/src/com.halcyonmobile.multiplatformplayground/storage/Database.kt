package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot

// todo add paging
interface Database {

    suspend fun getApplications(): List<Application>

    suspend fun createApplication(application: Application)

    suspend fun updateApplication(application: Application)

    suspend fun getApplication(id: Long): Application

    suspend fun getApplications(name: String, categoryId: Long): List<Application>

    suspend fun getCategories(): List<Category>

    suspend fun createScreenshot(screenshot: Screenshot)
}