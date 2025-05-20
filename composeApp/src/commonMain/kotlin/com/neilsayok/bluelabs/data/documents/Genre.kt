package com.neilsayok.bluelabs.data.documents


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreFields(
    @SerialName("id") val id: Id? = null, @SerialName("type") val type: Type? = null
)

@Serializable
data class Id(
    @SerialName("stringValue") val stringValue: String? = null
)

@Serializable
data class Type(
    @SerialName("stringValue") val stringValue: String? = null
)
        
    
