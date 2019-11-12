package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.NotFound
import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.log
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

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

//        create(listOf(ApplicationTable, CategoryTable, ScreenshotTable))
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

    override suspend fun createApplication(application: Application) {
        withContext(dispatcher) {
            transaction {
                ApplicationTable.insert {
                    it[id] = application.id
                    it[name] = application.name
                    it[developer] = application.developer
                    it[icon] = application.icon
                    it[rating] = application.rating?.toBigDecimal()
                    it[ratingCount] = application.ratingCount
                    it[storeUrl] = application.storeUrl
                    it[description] = application.description
                    it[downloads] = application.downloads
                    it[version] = application.version
                    it[size] = application.size
                    it[favourite] = application.favourite
                    it[categoryId] = application.category?.id
                    // todo add screenshots also
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
                    it[icon] = application.icon
                    it[rating] = application.rating?.toBigDecimal()
                    it[ratingCount] = application.ratingCount
                    it[storeUrl] = application.storeUrl
                    it[description] = application.description
                    it[downloads] = application.downloads
                    it[version] = application.version
                    it[size] = application.size
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

    override suspend fun createScreenshot(screenshot: Screenshot) = withContext(dispatcher) {
        // todo implement this
    }
}