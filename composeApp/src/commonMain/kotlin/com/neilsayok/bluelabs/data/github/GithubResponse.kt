package com.neilsayok.bluelabs.data.github


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Serializable
data class GithubResponse(
    @SerialName("content") val content: String? = null,
    @SerialName("download_url") val downloadUrl: String? = null,
    @SerialName("encoding") val encoding: String? = null,
    @SerialName("git_url") val gitUrl: String? = null,
    @SerialName("html_url") val htmlUrl: String? = null,
    @SerialName("_links") val links: Links? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("path") val path: String? = null,
    @SerialName("sha") val sha: String? = null,
    @SerialName("size") val size: Int? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("url") val url: String? = null
)

@Serializable
data class Links(
    @SerialName("git") val git: String? = null,
    @SerialName("html") val html: String? = null,
    @SerialName("self") val self: String? = null
)


@OptIn(ExperimentalEncodingApi::class)
fun GithubResponse.getDecodedContent(): String {
    return content?.let {
        val cleanString = it
            .replace("\\s".toRegex(), "")
        Base64.decode(cleanString).decodeToString()
    } ?: ""
}