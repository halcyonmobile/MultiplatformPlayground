package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.model.ApplicationWithDetail
import com.halcyonmobile.multiplatformplayground.shared.util.File
import com.halcyonmobile.multiplatformplayground.shared.util.getStreamFromCacheFallbackOnRemote


internal class ApplicationRepository(
    private val localSource: ApplicationLocalSource,
    private val remoteSource: ApplicationRemoteSource
) {
    val favouritesStream = localSource.favouritesStream

    fun getDetailById(appId: Long) =
        getStreamFromCacheFallbackOnRemote(localSource.getDetailById(appId), remoteSourceOp = {
            remoteSource.getDetail(appId)
        }, cacheOperation = {
            localSource.cacheApplicationWithDetail(it)
        })

    fun getByCategory(categoryId: Long, page: Int = 0, perPage: Int = DEFAULT_PER_PAGE) =
        getStreamFromCacheFallbackOnRemote(
            perPage,
            localSource.getByCategory(categoryId, page, perPage),
            remoteSourceOp = {
                remoteSource.get(categoryId, page, perPage)
            },
            cacheOperation = {
                localSource.cacheApplications(it)
            }
        )

    suspend fun create(
        application: ApplicationWithDetail,
        icon: File,
        screenshots: List<File>
    ) =
        remoteSource.create(
            application,
            icon,
            screenshots
        ).also { localSource.cacheApplicationWithDetail(application) }

    suspend fun updateFavourites(applicationId: Long, isFavourite: Boolean) =
        localSource.updateFavourites(applicationId, isFavourite)

    companion object {
        const val DEFAULT_PER_PAGE = 10
    }
}