package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.*

// todo add paging
internal interface LocalSource {

    suspend fun getApplications(): List<Application>

    suspend fun createApplication(applicationWithDetail: ApplicationWithDetail)

    suspend fun updateApplication(application: Application)

    suspend fun getApplication(id: Long): Application

    suspend fun getApplicationWithDetail(id: Long): ApplicationDetailResponse

    suspend fun getApplications(name: String, categoryId: Long): List<Application>

    suspend fun getCategories(): List<Category>

    suspend fun createScreenshot(screenshot: Screenshot)

    suspend fun getScreenshots(screenshotIds: List<Long>): List<Screenshot>
}