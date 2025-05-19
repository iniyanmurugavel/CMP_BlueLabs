package com.neilsayok.bluelabs.domain.firebase

import com.neilsayok.bluelabs.BuildKonfig
import com.neilsayok.bluelabs.data.bloglist.BlogListFields
import com.neilsayok.bluelabs.data.bloglist.BlogListResponse
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.domain.util.getResponse
import com.neilsayok.bluelabs.util.networkFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow

class FirebaseRepo(private val httpClient: HttpClient) {

    suspend fun getAllBlogs() : Flow<Response<BlogListResponse<BlogListFields>>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "firestore.googleapis.com"
            path("v1/projects/bluelabs-41aef/databases/(default)/documents/blogs")
            parameters.append("key", BuildKonfig.FIREBASE_AUTH_TOKEN)
        }.build()

        httpClient.get(url).getResponse<BlogListResponse<BlogListFields>>()
    }



}