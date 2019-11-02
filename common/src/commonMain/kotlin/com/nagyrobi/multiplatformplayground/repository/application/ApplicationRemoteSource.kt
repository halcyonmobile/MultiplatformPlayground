package com.nagyrobi.multiplatformplayground.repository.application

import com.nagyrobi.multiplatformplayground.api.ApplicationApi
import com.nagyrobi.multiplatformplayground.model.Application

class ApplicationRemoteSource internal constructor(private val applicationApi: ApplicationApi) {

    suspend fun get(categoryId: Long, offset: Int, perPage: Int) =
        applicationApi.getApplicationsByCategory(offset, perPage, categoryId)

    // todo implement create
//    suspend fun create(application: Application, screenshotList)

    suspend fun getDetail(id: Long) = applicationApi.getApplicationDetails(id)
}