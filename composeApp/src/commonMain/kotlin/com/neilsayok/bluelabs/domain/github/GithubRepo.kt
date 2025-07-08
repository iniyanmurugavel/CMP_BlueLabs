package com.neilsayok.bluelabs.domain.github

import com.neilsayok.bluelabs.BuildKonfig
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.domain.util.getResponse
import com.neilsayok.bluelabs.util.networkFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.authority
import io.ktor.http.headers
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow

class GithubRepo(private val httpClient: HttpClient) {



    suspend fun getContent(fileName: String): Flow<Response<GithubResponse>> = networkFlow {

        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "api.github.com"
            path("repos/NeilSayok/imagelib/contents/${fileName}")
        }.build()

        httpClient.get(url){
            headers {
                append("Accept", "application/vnd.github+json")
                append("Authorization", "Bearer ${BuildKonfig.GITHUB_TOKEN}")
                append("User-Agent", "request")

            }
        }.getResponse<GithubResponse>()
    }


    suspend fun getFilesFromFolder(folderName: String): Flow<Response<List<GithubResponse>>> = networkFlow {
        val url = URLBuilder(protocol = URLProtocol.HTTPS).apply {
            host = "api.github.com"
            path("repos/NeilSayok/imagelib/contents/${folderName}")
        }.build()

        httpClient.get(url){
            headers {
                append("Accept", "application/vnd.github+json")
                append("Authorization", "Bearer ${BuildKonfig.GITHUB_TOKEN}")
                append("User-Agent", "request")

            }
        }.getResponse<List<GithubResponse>>()
    }


}