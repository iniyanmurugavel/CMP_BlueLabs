package com.neilsayok.bluelabs.data.bloglist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseResponse<T>(
    @SerialName("documents") val documents: List<Document<T>?>? = emptyList()
)

@Serializable
data class Document<T>(
    @SerialName("createTime") val createTime: String? = null,
    @SerialName("fields") val fields: T? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("updateTime") val updateTime: String? = null
)
