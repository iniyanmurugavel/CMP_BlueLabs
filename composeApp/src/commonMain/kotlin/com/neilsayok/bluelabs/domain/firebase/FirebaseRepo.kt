package com.neilsayok.bluelabs.domain.firebase

import com.neilsayok.bluelabs.BuildKonfig
import com.neilsayok.bluelabs.data.bloglist.BlogListFields
import com.neilsayok.bluelabs.data.bloglist.BlogListResponse
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.domain.util.getResponse
import com.neilsayok.bluelabs.util.networkFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow

class FirebaseRepo(private val httpClient: HttpClient) {

    suspend fun getAllBlogs() : Flow<Response<BlogListResponse<BlogListFields>>> =  networkFlow {
        httpClient.get {
            url { path("https://firestore.googleapis.com/v1/projects/bluelabs-41aef/databases/(default)/documents/blogs?key=${BuildKonfig.FIREBASE_AUTH_TOKEN}") }
        }.getResponse<BlogListResponse<BlogListFields>>()
    }



}