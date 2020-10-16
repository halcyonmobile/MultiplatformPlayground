package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.model.ApplicationDetail
import com.halcyonmobile.multiplatformplayground.shared.util.File


internal class ApplicationRepository(
    private val remoteSource: ApplicationRemoteSource
) {
    suspend fun getByCategory(categoryId: Long, page: Int = 0, perPage: Int = DEFAULT_PER_PAGE) =
        remoteSource.get(categoryId, page, perPage)

    suspend fun getDetailById(appId: Long) = remoteSource.getDetail(appId)

    suspend fun create(application: ApplicationDetail, icon: File, screenshots: List<File>) =
        remoteSource.create(application, icon, screenshots)

    companion object {
        const val DEFAULT_PER_PAGE = 10
    }
}