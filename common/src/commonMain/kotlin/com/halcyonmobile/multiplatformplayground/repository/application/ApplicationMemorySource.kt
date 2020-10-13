package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.ApplicationDetail
import com.halcyonmobile.multiplatformplayground.model.ApplicationWithDetail
import com.halcyonmobile.multiplatformplayground.shared.util.extension.safeSubList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@UseExperimental(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ApplicationMemorySource : ApplicationLocalSource {

    private val _applications = ConflatedBroadcastChannel<List<CachedApplication>>(emptyList())
    private val applications
        get() = _applications.asFlow().distinctUntilChanged()

    override val favouritesStream
        get() = applications.map { applications ->
            applications.filter { it.application.favourite }.map { it.application }
        }.distinctUntilChanged()
            .flowOn(Dispatchers.Default)

    override suspend fun getById(id: Long) =
        _applications.valueOrNull?.firstOrNull { it.application.id == id }?.application

    override fun getDetailById(id: Long) = applications.map { applications ->
        applications.firstOrNull { it.application.id == id }?.toApplicationWithDetail()
    }.distinctUntilChanged()
        .flowOn(Dispatchers.Default)

    override suspend fun cacheApplicationWithDetail(applicationWithDetail: ApplicationWithDetail) {
        withContext(Dispatchers.Default) {
            val updatedApplications = _applications.value.toMutableList()
            val index =
                updatedApplications.indexOfFirst { it.application.id == applicationWithDetail.application.id }
            if (index < 0) {
                updatedApplications.add(applicationWithDetail.toCachedApplication())
            } else {
                updatedApplications[index] = applicationWithDetail.toCachedApplication()
            }

            _applications.offer(updatedApplications)
        }
    }

    override fun getByCategory(categoryId: Long, page: Int, limit: Int) =
        applications.map { applications ->
            applications.filter { it.application.categoryId == categoryId }.safeSubList(
                page * limit,
                (page + 1) * limit
            ).map { it.application }
        }.flowOn(Dispatchers.Default)

    override suspend fun cacheApplications(applications: List<Application>) {
        withContext(Dispatchers.Default) {
            _applications.offer(applications.map { CachedApplication(it) }.plus(_applications.value).distinct())
        }
    }

    override suspend fun updateFavourites(applicationId: Long, isFavourite: Boolean) {
        withContext(Dispatchers.Default) {
            val updatedApplications = _applications.value.map {
                if (it.application.id == applicationId) {
                    it.copy(
                        application = it.application.copy(favourite = isFavourite)
                    )
                } else {
                    it
                }
            }

            _applications.offer(updatedApplications)
        }
    }

    private data class CachedApplication(
        val application: Application,
        val applicationDetail: ApplicationDetail? = null
    )

    private fun CachedApplication.toApplicationWithDetail() =
        if (applicationDetail == null) null else ApplicationWithDetail(
            application = application,
            applicationDetail = applicationDetail
        )

    private fun ApplicationWithDetail.toCachedApplication() =
        CachedApplication(application = application, applicationDetail = applicationDetail)
}
