package com.neilsayok.bluelabs.data.bloglist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlogListResponse<T>(
    @SerialName("documents")
    val documents: List<Document<T>?>? = emptyList()
)

@Serializable
data class Document<T>(
    @SerialName("createTime")
    val createTime: String? = null,
    @SerialName("fields")
    val fields: T? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("updateTime")
    val updateTime: String? = null
)

@Serializable
data class BlogListFields(
    @SerialName("author")
    val author: Author? = null,
    @SerialName("big_img")
    val bigImg: BigImg? = null,
    @SerialName("genre")
    val genre: Genre? = null,
    @SerialName("isPublished")
    val isPublished: IsPublished? = null,
    @SerialName("posted")
    val posted: Posted? = null,
    @SerialName("read_time")
    val readTime: ReadTime? = null,
    @SerialName("readme_file")
    val readmeFile: ReadmeFile? = null,
    @SerialName("tags")
    val tags: Tags? = null,
    @SerialName("tiny_img")
    val tinyImg: TinyImg? = null,
    @SerialName("title")
    val title: Title? = null,
    @SerialName("url_str")
    val urlStr: UrlStr? = null
)

@Serializable
data class Author(
    @SerialName("referenceValue")
    val referenceValue: String? = null
)

@Serializable
data class BigImg(
    @SerialName("stringValue")
    val stringValue: String? = null
)

@Serializable
data class Genre(
    @SerialName("referenceValue")
    val referenceValue: String? = null
)

@Serializable
data class IsPublished(
    @SerialName("booleanValue")
    val booleanValue: Boolean? = false
)

@Serializable
data class Posted(
    @SerialName("timestampValue")
    val timestampValue: String? = null
)

@Serializable
data class ReadTime(
    @SerialName("integerValue")
    val integerValue: String? = "0"
)

@Serializable
data class ReadmeFile(
    @SerialName("stringValue")
    val stringValue: String? = null
)

@Serializable
data class Tags(
    @SerialName("arrayValue")
    val arrayValue: ArrayValue? = null
)

@Serializable
data class ArrayValue(
    @SerialName("values")
    val values: List<Value?>? = emptyList()
)

@Serializable
data class Value(
    @SerialName("stringValue")
    val stringValue: String? = null
)


@Serializable
data class TinyImg(
    @SerialName("stringValue")
    val stringValue: String? = null
)

@Serializable
data class Title(
    @SerialName("stringValue")
    val stringValue: String? = null
)

@Serializable
data class UrlStr(
    @SerialName("stringValue")
    val stringValue: String? = null
)
        
    
