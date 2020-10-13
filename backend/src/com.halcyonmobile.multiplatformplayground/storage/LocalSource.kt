package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.*

// todo add paging
internal interface LocalSource {

    suspend fun getApplications(): List<Application>

    suspend fun getApplications(page: Int, perPage: Int): List<Application>

    suspend fun saveApplication(applicationRequest: ApplicationRequest)

    suspend fun updateApplication(application: Application)

    suspend fun getApplication(id: Long): Application

    suspend fun getApplicationWithDetail(id: Long): ApplicationDetailResponse

    suspend fun getApplications(name: String, categoryId: Long): List<Application>

    suspend fun saveCategory(category: Category): Long

    suspend fun getCategories(): List<Category>

    suspend fun getCategory(id: Long): Category

    suspend fun saveScreenshot(screenshot: Screenshot, appId: Long): Long

    suspend fun getScreenshots(screenshotIds: List<Long>): List<Screenshot>
}