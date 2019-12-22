package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.NotFound
import com.halcyonmobile.multiplatformplayground.model.*
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
            ApplicationTable.leftJoin(CategoryTable)
                .selectAll()
                .map {
                    // todo implement screenshots
                    it.mapRowToApplication(it.mapRowToCategory(), emptyList())
                }
        }
    }

    override suspend fun createApplication(applicationWithDetail: ApplicationWithDetail) {
        withContext(dispatcher) {
            transaction {
                with(applicationWithDetail) {
                    ApplicationTable.insert {
                        it[id] = application.id
                        it[name] = application.name
                        it[developer] = application.developer
                        it[icon] = applicationDetail.icon
                        it[rating] = applicationDetail.rating.toBigDecimal()
                        it[ratingCount] = applicationDetail.ratingCount
                        it[storeUrl] = applicationDetail.storeUrl
                        it[description] = applicationDetail.description
                        it[downloads] = applicationDetail.downloads
                        it[version] = applicationDetail.version
                        it[size] = applicationDetail.size
                        it[favourite] = application.favourite
                        it[categoryId] = application.category?.id
                        // todo add screenshots also
                    }
                }
            }
        }
    }

    override suspend fun updateApplication(application: Application) {
        withContext(dispatcher) {
            transaction {
                ApplicationTable.update({ ApplicationTable.id eq application.id }) {
                    it[id] = application.id
                    it[name] = application.name
                    it[developer] = application.developer
                    it[favourite] = application.favourite
                    it[categoryId] = application.category?.id
                    // todo update screenshots also
                }
            }
        }
    }

    override suspend fun getApplication(id: Long): Application = withContext(dispatcher) {
        transaction {
            // todo implement screenshots
            ApplicationTable.leftJoin(CategoryTable)
                .select { ApplicationTable.id eq id }
                .singleOrNull()?.let {
                    it.mapRowToApplication(it.mapRowToCategory(), emptyList())
                } ?: throw NotFound()
        }
    }

    override suspend fun getApplicationWithDetail(id: Long): ApplicationDetailResponse =
        withContext(dispatcher) {
            transaction {
                // todo implement screenshots
                ApplicationTable.leftJoin(CategoryTable)
                    .select { ApplicationTable.id eq id }
                    .singleOrNull()?.let {
                        it.mapRowToApplicationDetailResponse(it.mapRowToCategory(), emptyList())
                    } ?: throw NotFound()
            }
        }

    override suspend fun getApplications(name: String, categoryId: Long): List<Application> =
        withContext(dispatcher) {
            transaction {
                //todo add screenshots
                ApplicationTable.select { (ApplicationTable.name eq name) and (ApplicationTable.categoryId eq categoryId) }
                    .map { it.mapRowToApplication(it.mapRowToCategory(), emptyList()) }
            }
        }

    override suspend fun getCategories(): List<Category> = withContext(dispatcher) {
        transaction {
            CategoryTable.selectAll().map { it.mapRowToCategory() }.toList()
        }
    }

    override suspend fun saveScreenshot(screenshot: Screenshot) = withContext(dispatcher) {
        // todo implement this
    }

    override suspend fun getScreenshots(screenshotIds: List<Long>): List<Screenshot> =
        withContext(dispatcher) {
            transaction {
                ScreenshotTable.selectAll().map { it.mapRowToScreenshot() }.toList()
            }
        }
}