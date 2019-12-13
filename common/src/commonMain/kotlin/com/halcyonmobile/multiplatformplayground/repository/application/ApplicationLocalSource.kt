package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.ApplicationWithDetail
import kotlinx.coroutines.flow.Flow


/**
 * It's used to cache the data locally so less network request is required.
 */
interface ApplicationLocalSource {

    /**
     * Stream of all favorite applications.
     * When an application is added or removed from favorites the updated list will be emitted from this stream.
     */
    val favouritesStream: Flow<List<Application>>

    /**
     * Emits an [Application] with it's most basic fields.
     * If there is not such application cached, it will emit null.
     */
    suspend fun getById(id: Long): Application?

    /**
     * Emits an [ApplicationWithDetail] with all of it's field.
     * If there is not such application cached, it will emit null.
     */
    fun getDetailById(id: Long): Flow<ApplicationWithDetail?>

    /**
     * Saves an [ApplicationWithDetail] with every bit of details.
     * Throws [IllegalArgumentException] if [ApplicationDetail] is null
     */
    suspend fun cacheApplicationWithDetail(applicationWithDetail: ApplicationWithDetail)

    /**
     * Saves a [List] of [Application]s
     */
    suspend fun cacheApplications(applications: List<Application>)

    /**
     * Emits a [limit] number of Applications with their most basic fields on the given [page].
     */
    fun getByCategory(categoryId: Long, page: Int, limit: Int): Flow<List<Application>>

    /**
     * Updates the favorite field of the [Application] associated with the given [applicationId] to [isFavourite].
     * The [favouritesStream] will be notified with the updated list.
     *
     */
    suspend fun updateFavourites(applicationId: Long, isFavourite: Boolean)
}