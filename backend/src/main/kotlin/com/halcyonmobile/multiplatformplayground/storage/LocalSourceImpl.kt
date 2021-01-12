package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.storage.file.FileStorage
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
import com.halcyonmobile.multiplatformplayground.util.getPage
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import kotlin.coroutines.CoroutineContext

@OptIn(
    KtorExperimentalAPI::class, ObsoleteCoroutinesApi::class
)
internal class LocalSourceImpl(
    private val fileStorage: FileStorage,
    application: io.ktor.application.Application
) : LocalSource {
    private val dispatcher: CoroutineContext

    init {
        val config = application.environment.config.config("database")
        val url = System.getenv("JDBC_DATABASE_URL")
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
            SchemaUtils.createMissingTablesAndColumns(ApplicationTable, CategoryTable, ScreenshotTable)
        }
    }

    override suspend fun getApplications(): List<Application> = withContext(dispatcher) {
        transaction {
            ApplicationEntity.all().map { it.toApplication() }
        }
    }

    override suspend fun getApplications(
        page: Int,
        perPage: Int,
        categoryId: Long
    ): List<Application> =
        getApplications().filter { it.categoryId == categoryId }.getPage(page, perPage)

    override suspend fun saveApplication(applicationRequest: ApplicationRequest) =
        withContext(dispatcher) {
            transaction {
                val category = CategoryEntity[applicationRequest.categoryId.toInt()]
                ApplicationEntity.new {
                    name = applicationRequest.name
                    developer = applicationRequest.developer
                    icon = ""
                    rating = applicationRequest.rating.toBigDecimal()
                    ratingCount = applicationRequest.ratingCount
                    storeUrl = applicationRequest.storeUrl
                    description = applicationRequest.description
                    downloads = applicationRequest.downloads
                    version = applicationRequest.version
                    size = applicationRequest.size
                    favourite = applicationRequest.favourite
                    this.category = category
                }.id.value.toLong()
            }
        }

    override suspend fun updateApplication(application: Application) {
        withContext(dispatcher) {
            transaction {
                ApplicationEntity[application.id.toInt()].let {
                    it.name = application.name
                    it.developer = application.developer
                    it.rating = application.rating.toBigDecimal()
                    it.favourite = application.favourite
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

    override suspend fun updateCategory(category: Category) = withContext(dispatcher) {
        transaction {
            CategoryEntity[category.id.toInt()].let{
                it.name = category.name
                it.icon = category.icon
            }
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

    override suspend fun saveIcon(icon: File, appId: Long) = withContext(dispatcher) {
        val iconUrl = fileStorage.save(icon)
        transaction {
            val application = ApplicationEntity[appId.toInt()]
            application.icon = iconUrl
        }
    }

    override suspend fun saveScreenshot(appId: Long, name: String, image: File) {
        withContext(dispatcher) {
            val imageUrl = fileStorage.save(image)
            transaction {
                val application = ApplicationEntity[appId.toInt()]
                ScreenshotEntity.new {
                    this.name = name
                    this.image = imageUrl
                    this.application = application
                }
            }
        }
    }

    override suspend fun getScreenshots(screenshotIds: List<Long>): List<Screenshot> =
        withContext(dispatcher) {
            transaction {
                ScreenshotEntity.all().map { it.toScreenshot() }
            }
        }

    override suspend fun getFavourites(): List<Application> = withContext(dispatcher) {
        transaction {
            ApplicationEntity.all().filter { it.favourite }.map { it.toApplication() }
        }
    }
}