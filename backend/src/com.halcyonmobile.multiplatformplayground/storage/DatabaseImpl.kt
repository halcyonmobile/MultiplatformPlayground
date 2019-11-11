package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.log
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.squash.connection.DatabaseConnection
import org.jetbrains.squash.connection.transaction
import org.jetbrains.squash.dialects.h2.H2Connection
import org.jetbrains.squash.expressions.AllTableColumnsExpression
import org.jetbrains.squash.expressions.eq
import org.jetbrains.squash.query.select
import org.jetbrains.squash.query.where
import org.jetbrains.squash.statements.insertInto
import org.jetbrains.squash.statements.update
import org.jetbrains.squash.statements.values
import kotlin.coroutines.CoroutineContext

internal class DatabaseImpl(application: io.ktor.application.Application) : Database {
    private val dispatcher: CoroutineContext
    private val connectionPool: HikariDataSource
    private val connection: DatabaseConnection

    init {
        val config = application.environment.config.config("database")
        val url = config.property("connection").getString()
        val poolSize = config.property("poolSize").getString().toInt()
        application.log.info("Connecting to db at $url")

        dispatcher = newFixedThreadPoolContext(poolSize, "database-pool")
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = url
            maximumPoolSize = poolSize
            validate()
        }

        connectionPool = HikariDataSource(hikariConfig)

        connection = H2Connection { connectionPool.connection }
        connection.transaction {
            databaseSchema().create(listOf(ApplicationTable, CategoryTable, ScreenshotTable))
        }
    }

    override suspend fun getApplications(): List<Application> = withContext(dispatcher) {
        connection.transaction {
            ApplicationTable.select(AllTableColumnsExpression(ApplicationTable)).execute()
                .map { it.mapRowToApplication() }.toList()
        }
    }

    override suspend fun createApplication(application: Application) = withContext(dispatcher) {
        connection.transaction {
            insertInto(ApplicationTable).values {
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
                // todo add foreign key id's
            }.execute()
        }
    }

    override suspend fun updateApplication(application: Application) {
        withContext(dispatcher) {
            connection.transaction {
                update(ApplicationTable).where { ApplicationTable.id eq application.id }.apply {
                    set(ApplicationTable.id, application.id)
                    set(ApplicationTable.name, application.name)
                    set(ApplicationTable.developer, application.developer)
                    set(ApplicationTable.icon, application.icon)
                    set(ApplicationTable.rating, application.rating?.toBigDecimal())
                    set(ApplicationTable.ratingCount, application.ratingCount)
                    set(ApplicationTable.storeUrl, application.storeUrl)
                    set(ApplicationTable.description, application.description)
                    set(ApplicationTable.downloads, application.downloads)
                    set(ApplicationTable.version, application.version)
                    set(ApplicationTable.size, application.size)
                    set(ApplicationTable.favourite, application.favourite)
                    // todo update foreign key's also
                }
            }
        }
    }

    override suspend fun getApplication(id: Long): Application = withContext(dispatcher) {
        connection.transaction {
            ApplicationTable.select { ApplicationTable.id eq id }.execute().single()
                .mapRowToApplication()
        }
    }

    override suspend fun getApplications(name: String, categoryId: Long): List<Application> =
        withContext(dispatcher) {
            connection.transaction {
                // todo add categoryId too
                ApplicationTable.select { ApplicationTable.name eq name }.execute()
                    .map { it.mapRowToApplication() }.toList()
            }
        }

    override suspend fun getCategories(): List<Category> = withContext(dispatcher) {
        connection.transaction {
            CategoryTable.select(AllTableColumnsExpression(CategoryTable)).execute()
                .map { it.mapRowToCategory() }.toList()
        }
    }

    override suspend fun createScreenshot(screenshot: Screenshot) = withContext(dispatcher) {
        // todo implement this
    }
}