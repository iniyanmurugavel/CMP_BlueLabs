package com.neilsayok.bluelabs.pages.indexer.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndexItem(
    @SerialName("body") val body: String,
    @SerialName("tags") val tags: List<String>,
    @SerialName("title") val title: String,
    @SerialName("author_uid") val authorUid: String,
    @SerialName("author_name") val authorName: String,
    @SerialName("url") val url: String
)