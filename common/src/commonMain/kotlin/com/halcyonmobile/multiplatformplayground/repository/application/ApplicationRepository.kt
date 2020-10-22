package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.UploadApplicationModel


internal class ApplicationRepository(
    private val remoteSource: ApplicationRemoteSource
) {
    suspend fun getByCategory(categoryId: Long, page: Int = 0, perPage: Int = DEFAULT_PER_PAGE) =
        remoteSource.get(categoryId, page, perPage)

    suspend fun getDetailById(appId: Long) = remoteSource.getDetail(appId)

    suspend fun create(uploadApplicationModel: UploadApplicationModel) =
        remoteSource.create(uploadApplicationModel)

    suspend fun update(application: Application) = remoteSource.update(application)

    companion object {
        const val DEFAULT_PER_PAGE = 10
    }
}