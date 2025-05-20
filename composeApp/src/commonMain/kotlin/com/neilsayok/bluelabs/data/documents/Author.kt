package com.neilsayok.bluelabs.data.documents


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthorFields(
    @SerialName("bio") val bio: Bio?,
    @SerialName("description") val description: Description?,
    @SerialName("img_url") val imgUrl: ImgUrl?,
    @SerialName("joined") val joined: Joined?,
    @SerialName("location") val location: Location?,
    @SerialName("name") val name: Name?,
    @SerialName("pass") val pass: Pass?,
    @SerialName("profiles") val profiles: Profiles?,
    @SerialName("uid") val uid: Uid?
)

@Serializable
data class Bio(
    @SerialName("stringValue") val stringValue: String?
)

@Serializable
data class Description(
    @SerialName("stringValue") val stringValue: String?
)

@Serializable
data class ImgUrl(
    @SerialName("stringValue") val stringValue: String?
)

@Serializable
data class Joined(
    @SerialName("timestampValue") val timestampValue: String?
)

@Serializable
data class Location(
    @SerialName("stringValue") val stringValue: String?
)

@Serializable
data class Name(
    @SerialName("stringValue") val stringValue: String?
)

@Serializable
data class Pass(
    @SerialName("stringValue") val stringValue: String?
)

@Serializable
data class Profiles(
    @SerialName("referenceValue") val referenceValue: String?
)

@Serializable
data class Uid(
    @SerialName("stringValue") val stringValue: String?
)
        
    
