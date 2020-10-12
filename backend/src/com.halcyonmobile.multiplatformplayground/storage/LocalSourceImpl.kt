package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.storage.model.application.*
import com.halcyonmobile.multiplatformplayground.storage.model.application.ApplicationTable
import com.halcyonmobile.multiplatformplayground.storage.model.category.CategoryEntity
import com.halcyonmobile.multiplatformplayground.storage.model.category.CategoryTable
import com.halcyonmobile.multiplatformplayground.storage.model.category.toCategory
import com.halcyonmobile.multiplatformplayground.storage.model.screenshot.ScreenshotEntity
import com.halcyonmobile.multiplatformplayground.storage.model.screenshot.ScreenshotTable
import com.halcyonmobile.multiplatformplayground.storage.model.screenshot.toScreenshot
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.log
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

@UseExperimental(KtorExperimentalAPI::class, ObsoleteCoroutinesApi::class)
internal class LocalSourceImpl(application: io.ktor.application.Application) : LocalSource {
    private val dispatcher: CoroutineContext

    init {
        val config = application.environment.config.config("database")
        val url = config.property("connection").getString()
        val driver = config.property("driver").getString()
        val poolSize = config.property("poolSize").getString().toInt()
        application.log.info("Connecting to db at $url")

        dispatcher = newFixedThreadPoolContext(poolSize, "database-pool")
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = url
            maximumPoolSize = poolSize
            driverClassName = driver
            validate()
        }

        Database.connect(HikariDataSource(hikariConfig))

        transaction {
            SchemaUtils.create(ApplicationTable, CategoryTable, ScreenshotTable)
        }
    }

    override suspend fun getApplications(): List<Application> = withContext(dispatcher) {
        transaction {
            ApplicationEntity.all().map { it.toApplication() }
        }
    }

    override suspend fun saveApplication(applicationWithDetail: ApplicationWithDetail) {
        withContext(dispatcher) {
            transaction {
                with(applicationWithDetail) {
                    val category = CategoryEntity[application.category.id.toInt()]
                    ApplicationEntity.new {
                        name = application.name
                        developer = application.developer
                        icon = applicationDetail.icon
                        rating = applicationDetail.rating.toBigDecimal()
                        ratingCount = applicationDetail.ratingCount
                        storeUrl = applicationDetail.storeUrl
                        description = applicationDetail.description
                        downloads = applicationDetail.downloads
                        version = applicationDetail.version
                        size = applicationDetail.size
                        favourite = application.favourite
                        this.category = category
                        // todo add screenshots also
                    }
                }
            }
        }
    }

    override suspend fun updateApplication(application: Application) {
        withContext(dispatcher) {
            transaction {
                ApplicationEntity[application.id.toInt()].let {
                    it.name = application.name
                    it.developer = application.developer
                    it.favourite = application.favourite
                    // todo update screenshots also
                }
            }
        }
    }

    override suspend fun getApplication(id: Long): Application = withContext(dispatcher) {
        transaction {
            ApplicationEntity[id.toInt()].toApplication()
        }
    }

    override suspend fun getApplicationWithDetail(id: Long): ApplicationDetailResponse =
        withContext(dispatcher) {
            transaction {
                ApplicationEntity[id.toInt()].toApplicationDetailResponse()
            }
        }

    override suspend fun saveCategory(category: Category) = withContext(dispatcher) {
        transaction {
            CategoryEntity.new {
                name = category.name
                icon = category.icon
            }.id.value.toLong()
        }
    }

    override suspend fun getApplications(name: String, categoryId: Long): List<Application> =
        withContext(dispatcher) {
            transaction {
                ApplicationEntity.find { ApplicationTable.name eq name }
                    .filter { it.category.id.value.toLong() == categoryId }
                    .map { it.toApplication() }
            }
        }

    override suspend fun getCategories(): List<Category> = withContext(dispatcher) {
        transaction {
            CategoryEntity.all().map { it.toCategory() }
        }
    }

    override suspend fun getCategory(id: Long): Category = withContext(dispatcher) {
        transaction {
            CategoryEntity[id.toInt()].toCategory()
        }
    }

    override suspend fun saveScreenshot(screenshot: Screenshot, appId: Long) =
        withContext(dispatcher) {
            transaction {
                val application = ApplicationEntity[appId.toInt()]
                ScreenshotEntity.new {
                    name = screenshot.name
                    image = screenshot.image
                    this.application = application
                }.id.value.toLong()
            }
        }

    override suspend fun getScreenshots(screenshotIds: List<Long>): List<Screenshot> =
        withContext(dispatcher) {
            transaction {
                ScreenshotEntity.all().map { it.toScreenshot() }
            }
        }
}