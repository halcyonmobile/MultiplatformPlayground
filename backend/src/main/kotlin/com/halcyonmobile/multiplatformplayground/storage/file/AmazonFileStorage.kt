package com.halcyonmobile.multiplatformplayground.storage.file

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(KtorExperimentalAPI::class)
class AmazonFileStorage : FileStorage {

    private val client: AmazonS3Client

    private val bucketName: String = System.getenv("bucketName")

    init {
        val region = System.getenv("region")
        val accessKey = System.getenv("accessKey")
        val secretKey = System.getenv("secretKey")

        val credentials = BasicAWSCredentials(accessKey, secretKey)
        client = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.fromName(region))
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build() as AmazonS3Client
    }

    override suspend fun save(file: File): String =
        withContext(Dispatchers.IO) {
            val objectRequest = PutObjectRequest(
                bucketName,
                file.name,
                file
            ).withCannedAcl(CannedAccessControlList.PublicRead)

            with(client) {
                putObject(objectRequest)
                getResourceUrl(bucketName, file.name)
            }
        }
}
