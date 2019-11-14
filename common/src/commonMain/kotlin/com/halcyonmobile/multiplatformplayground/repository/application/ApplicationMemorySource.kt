package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.repository.NoCacheFoundException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlin.math.min


@FlowPreview
@ExperimentalCoroutinesApi
internal class ApplicationMemorySource : ApplicationLocalSource {

    private val applicationsById = mutableMapOf<Long, Application>()
    private val applicationWithDetailsById = mutableMapOf<Long, Application>()
    private val applicationsByCategoryId = mutableMapOf<Long, List<Application>>()

    private val _favouritesStream =
        ConflatedBroadcastChannel<List<Application>>().apply { offer(emptyList()) }
    override val favouritesStream = _favouritesStream.openSubscription().consumeAsFlow()

    private val applicationDetailChannel = ConflatedBroadcastChannel<Application>()
    private val applicationDetailFlow = applicationDetailChannel.openSubscription().consumeAsFlow()

    override suspend fun getById(id: Long): Application =
        applicationsById[id] ?: throw NoCacheFoundException()

    override suspend fun getDetailById(id: Long): Flow<Application> =
        flowOf(applicationWithDetailsById[id] ?: throw  NoCacheFoundException())
            .onCompletion { emitAll(applicationDetailFlow) }

    override suspend fun cacheApplicationWithDetail(application: Application): Flow<Application> {
        application.category?.id?.let { categoryId ->
            val applicationsByCategoryId =
                applicationsByCategoryId[categoryId]?.toMutableList()?.also { applications ->
                    applications.indexOfFirst { application.id == it.id }.let { index ->
                        applications.removeAt(index)
                        applications.add(index, application)
                    }
                }
            if (applicationsByCategoryId != null) {
                this.applicationsByCategoryId[categoryId] = applicationsByCategoryId
            } else {
                this.applicationsByCategoryId.remove(categoryId)
            }
        }
        cacheApplicationById(application)
        applicationWithDetailsById[application.id] = application

        return getDetailById(application.id)
    }

    private fun cacheApplicationById(application: Application) {
        applicationsById[application.id] = application
    }

    override suspend fun getByCategory(categoryId: Long, limit: Int): List<Application> =
        applicationsByCategoryId[categoryId]?.take(limit) ?: throw NoCacheFoundException()

    override suspend fun getMoreByCategory(
        categoryId: Long,
        page: Int,
        limit: Int
    ): List<Application> {
        val fromIndex = (page - 1) * limit
        val toIndex = min(
            page * limit, (applicationsByCategoryId[categoryId]?.size
                ?: 1)
        )
        if (fromIndex >= toIndex) {
            throw NoCacheFoundException()
        }
        return applicationsByCategoryId[categoryId]?.subList(fromIndex, toIndex)
            ?: throw NoCacheFoundException()
    }

    override suspend fun replaceByCategory(
        categoryId: Long,
        applications: List<Application>
    ): List<Application> {
        applicationsByCategoryId[categoryId] = applications.toMutableList()
        applications.forEach { cacheApplicationById(it) }
        return applications
    }

    override suspend fun addMoreByCategory(
        categoryId: Long,
        applications: List<Application>
    ): List<Application> {
        applicationsByCategoryId[categoryId] =
            applicationsByCategoryId[categoryId]?.plus(applications) ?: applications.toList()
        applications.forEach { cacheApplicationById(it) }
        return applications
    }

    override suspend fun saveToMemory(application: Application): Application {
        // TODO we should check this because the application is only stored in the applicationsByCategoryId map
        applicationsByCategoryId[application.category?.id]?.plus(application)
            ?: listOf(application)
        return application
    }

    override suspend fun updateFavourites(applicationId: Long, isFavourite: Boolean) {
        val oldApplication = applicationsById.values.first { it.id == applicationId }
        val updatedApplication = oldApplication.copy(favourite = isFavourite)

        applicationsById[oldApplication.id] = updatedApplication

        val updatedDetailApplication =
            applicationWithDetailsById[applicationId]?.let { oldApplicationWithDetail ->
                oldApplicationWithDetail.copy(favourite = isFavourite).apply {
                    // Category and Screenshots are not part of the main constructor, so they are not part of the copy method either
                    category = oldApplicationWithDetail.category
                    screenshots = oldApplicationWithDetail.screenshots
                }
            }
        updatedDetailApplication?.let {
            applicationWithDetailsById[applicationId] = it
        }

        oldApplication.category?.id?.let { categoryId ->
            val applicationsByCategoryId =
                applicationsByCategoryId[categoryId]?.toMutableList()?.also { applications ->
                    applications.indexOf(oldApplication).also { position ->
                        applications.removeAt(position)
                        applications.add(position, updatedApplication)
                    }
                }
            if (applicationsByCategoryId != null) {
                this.applicationsByCategoryId[categoryId] = applicationsByCategoryId
            } else {
                this.applicationsByCategoryId.remove(categoryId)
            }
        }
        _favouritesStream.offer(applicationsById.values.distinct().filter { it.favourite })
        updatedDetailApplication?.let {
            applicationDetailChannel.offer(updatedDetailApplication)
        }
    }
}