package com.neilsayok.bluelabs.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("documents")
    val documents: List<Document?>? = null
) {
    @Serializable
    data class Document(
        @SerialName("createTime")
        val createTime: String? = null,
        @SerialName("fields")
        val fields: Fields? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("updateTime")
        val updateTime: String? = null
    ) {
        @Serializable
        data class Fields(
            @SerialName("id")
            val id: Id? = null,
            @SerialName("type")
            val type: Type? = null
        ) {
            @Serializable
            data class Id(
                @SerialName("stringValue")
                val stringValue: String? = null
            )

            @Serializable
            data class Type(
                @SerialName("stringValue")
                val stringValue: String? = null
            )
        }
    }
}