package com.neilsayok.bluelabs.data.documents


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class IndexFields(
    @SerialName("index") val index: Index? = null
)

@Serializable
data class Index(
    @SerialName("stringValue") val stringValue: String? = null
)
        
    
