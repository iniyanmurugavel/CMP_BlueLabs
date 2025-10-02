package com.neilsayok.bluelabs.domain.firebase

import com.neilsayok.bluelabs.BuildKonfig
import com.neilsayok.bluelabs.data.bloglist.FirebaseResponse
import com.neilsayok.bluelabs.data.documents.AuthorFields
import com.neilsayok.bluelabs.data.documents.BlogFields
import com.neilsayok.bluelabs.data.documents.GenreFields
import com.neilsayok.bluelabs.data.documents.IndexFields
import com.neilsayok.bluelabs.data.documents.ProfileFields
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.domain.util.getResponse
import com.neilsayok.bluelabs.util.networkFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow

class FirebaseRepo(private val httpClient: HttpClient) {

    suspend fun getAllBlogs(): Flow<Response<FirebaseResponse<BlogFields>>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "firestore.googleapis.com"
            path("v1/projects/bluelabs-41aef/databases/(default)/documents/blogs")
            parameters.append("key", BuildKonfig.FIREBASE_AUTH_TOKEN)
        }.build()

        httpClient.get(url).getResponse<FirebaseResponse<BlogFields>>()
    }


    suspend fun getAllAuthor(): Flow<Response<FirebaseResponse<AuthorFields>>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "firestore.googleapis.com"
            path("v1/projects/bluelabs-41aef/databases/(default)/documents/author")
            parameters.append("key", BuildKonfig.FIREBASE_AUTH_TOKEN)
        }.build()

        httpClient.get(url).getResponse<FirebaseResponse<AuthorFields>>()
    }


    suspend fun getAllIndex(): Flow<Response<FirebaseResponse<IndexFields>>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "firestore.googleapis.com"
            path("v1/projects/bluelabs-41aef/databases/(default)/documents/index")
            parameters.append("key", BuildKonfig.FIREBASE_AUTH_TOKEN)
        }.build()

        httpClient.get(url).getResponse<FirebaseResponse<IndexFields>>()
    }

    suspend fun getAllProfiles(): Flow<Response<FirebaseResponse<ProfileFields>>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "firestore.googleapis.com"
            path("v1/projects/bluelabs-41aef/databases/(default)/documents/profiles")
            parameters.append("key", BuildKonfig.FIREBASE_AUTH_TOKEN)
        }.build()

        httpClient.get(url).getResponse<FirebaseResponse<ProfileFields>>()
    }

    suspend fun getAllGenre(): Flow<Response<FirebaseResponse<GenreFields>>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "firestore.googleapis.com"
            path("v1/projects/bluelabs-41aef/databases/(default)/documents/genre")
            parameters.append("key", BuildKonfig.FIREBASE_AUTH_TOKEN)
        }.build()

        httpClient.get(url).getResponse<FirebaseResponse<GenreFields>>()
    }

    suspend fun updateIndex(indexData: String): Flow<Response<Any>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "firestore.googleapis.com"
            path("v1/projects/bluelabs-41aef/databases/(default)/documents/index/blog-index")
            parameters.append("key", BuildKonfig.FIREBASE_AUTH_TOKEN)
        }.build()

        val requestBody = mapOf(
            "fields" to mapOf(
                "index" to mapOf(
                    "stringValue" to indexData
                )
            )
        )

        httpClient.patch(url) {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.getResponse<Any>()
    }

    suspend fun createBlog(blogData: String): Flow<Response<Any>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "firestore.googleapis.com"
            path("v1/projects/bluelabs-41aef/databases/(default)/documents/blogs")
            parameters.append("key", BuildKonfig.FIREBASE_AUTH_TOKEN)
        }.build()

        httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(blogData)
        }.getResponse<Any>()
    }
}