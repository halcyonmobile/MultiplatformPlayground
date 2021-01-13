package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.ApplicationRequest
import com.halcyonmobile.multiplatformplayground.model.ApplicationDetailResponse
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import java.io.File

internal interface LocalSource {

    suspend fun getApplications(): List<Application>

    suspend fun getApplications(page: Int, perPage: Int, categoryId: Long): List<Application>

    suspend fun saveApplication(applicationRequest: ApplicationRequest): Long

    suspend fun deleteApplication(id: Long)

    suspend fun updateApplication(application: Application)

    suspend fun getApplication(id: Long): Application

    suspend fun getApplicationWithDetail(id: Long): ApplicationDetailResponse

    suspend fun getApplications(name: String, categoryId: Long): List<Application>

    suspend fun saveCategory(category: Category): Long

    suspend fun updateCategory(category: Category)

    suspend fun getCategories(): List<Category>

    suspend fun getCategory(id: Long): Category

    suspend fun saveIcon(icon: File, appId: Long)

    suspend fun saveScreenshot(appId: Long, name: String, image: File)

    suspend fun getScreenshots(screenshotIds: List<Long>): List<Screenshot>

    suspend fun getFavourites(): List<Application>
}