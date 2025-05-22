package com.neilsayok.bluelabs.data.documents


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProfileFields(
    @SerialName("facebook") val facebook: Facebook? = null,
    @SerialName("github") val github: Github? = null,
    @SerialName("instagram") val instagram: Instagram? = null,
    @SerialName("linkedin") val linkedin: Linkedin? = null,
    @SerialName("stackoverflow") val stackoverflow: Stackoverflow? = null,
    @SerialName("twitter") val twitter: Twitter? = null,
    @SerialName("uid") val uid: Uid? = null
)

@Serializable
data class Facebook(
    @SerialName("stringValue") val stringValue: String? = null
)

@Serializable
data class Github(
    @SerialName("stringValue") val stringValue: String? = null
)

@Serializable
data class Instagram(
    @SerialName("stringValue") val stringValue: String? = null
)

@Serializable
data class Linkedin(
    @SerialName("stringValue") val stringValue: String? = null
)

@Serializable
data class Stackoverflow(
    @SerialName("stringValue") val stringValue: String? = null
)

@Serializable
data class Twitter(
    @SerialName("stringValue") val stringValue: String? = null
)

        
    
