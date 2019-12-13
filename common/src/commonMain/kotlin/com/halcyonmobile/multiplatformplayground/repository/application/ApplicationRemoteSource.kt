package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.toApplicationWithDetail

class ApplicationRemoteSource internal constructor(private val applicationApi: ApplicationApi) {

    suspend fun get(categoryId: Long, offset: Int, perPage: Int) =
        applicationApi.getApplicationsByCategory(offset, perPage, categoryId)

    // todo implement create
//    suspend fun create(application: Application, screenshotList)

    suspend fun getDetail(id: Long) =
        applicationApi.getApplicationDetails(id).toApplicationWithDetail()
}