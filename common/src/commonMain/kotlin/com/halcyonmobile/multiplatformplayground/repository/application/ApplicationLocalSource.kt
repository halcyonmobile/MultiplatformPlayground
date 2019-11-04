package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.model.Application
import kotlinx.coroutines.flow.Flow
import com.halcyonmobile.multiplatformplayground.repository.NoCacheFoundException


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
     * If there is no such application cached, it will emit [NoCacheFoundException].
     */
    suspend fun getById(id: Long): Application

    /**
     * Emits an [Application] with all of it's field.
     * If there is not such application cached, it will emit [NoCacheFoundException].
     */
    suspend fun getDetailById(id: Long): Flow<Application>

    /**
     * Saves an [Application] with every bit of details of it then emits the saved data.
     * Every change to that [Application] will be emitted in this stream.
     */
    suspend fun cacheApplicationWithDetail(application: Application): Flow<Application>

    /**
     * Emits a [limit] number of Applications with their most basic fields.
     * If there is not enough application cached, it will emit [NoCacheFoundException].
     */
    suspend fun getByCategory(categoryId: Long, limit: Int): List<Application>

    /**
     * Skips [page] * [limit] from all application lists then emits a [limit] number of Applications with their most basic fields.
     * If there is not enough application cached, it will emit [NoCacheFoundException].
     */
    suspend fun getMoreByCategory(categoryId: Long, page: Int, limit: Int): List<Application>

    /**
     * Removes every application associated with [categoryId] then saves the new [applications] list with that [categoryId]
     */
    suspend fun replaceByCategory(
        categoryId: Long,
        applications: List<Application>
    ): List<Application>

    /**
     * Saves the [applications] list with the given [categoryId]
     */
    suspend fun addMoreByCategory(
        categoryId: Long,
        applications: List<Application>
    ): List<Application>

    /**
     * Saves the given [Application], similar to [cacheApplicationWithDetail], but it won't notify about the changes.
     */
    suspend fun saveToMemory(application: Application): Application

    /**
     * Updates the favorite field of the [Application] associated with the given [applicationId] to [isFavourite].
     * The [favouritesStream] will be notified with the updated list.
     */
    suspend fun updateFavourites(applicationId: Long, isFavourite: Boolean)
}