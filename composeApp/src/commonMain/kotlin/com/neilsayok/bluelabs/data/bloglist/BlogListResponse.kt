package com.neilsayok.bluelabs.data.bloglist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlogListResponse<T>(
    @SerialName("documents")
    val documents: List<T?>?
)

@Serializable
data class Document<T>(
    @SerialName("createTime")
    val createTime: String?,
    @SerialName("fields")
    val fields: T?,
    @SerialName("name")
    val name: String?,
    @SerialName("updateTime")
    val updateTime: String?
)

@Serializable
data class BlogListFields(
    @SerialName("author")
    val author: Author?,
    @SerialName("big_img")
    val bigImg: BigImg?,
    @SerialName("genre")
    val genre: Genre?,
    @SerialName("isPublished")
    val isPublished: IsPublished?,
    @SerialName("posted")
    val posted: Posted?,
    @SerialName("read_time")
    val readTime: ReadTime?,
    @SerialName("readme_file")
    val readmeFile: ReadmeFile?,
    @SerialName("tags")
    val tags: Tags?,
    @SerialName("tiny_img")
    val tinyImg: TinyImg?,
    @SerialName("title")
    val title: Title?,
    @SerialName("url_str")
    val urlStr: UrlStr?
)

@Serializable
data class Author(
    @SerialName("referenceValue")
    val referenceValue: String?
)

@Serializable
data class BigImg(
    @SerialName("stringValue")
    val stringValue: String?
)

@Serializable
data class Genre(
    @SerialName("referenceValue")
    val referenceValue: String?
)

@Serializable
data class IsPublished(
    @SerialName("booleanValue")
    val booleanValue: Boolean?
)

@Serializable
data class Posted(
    @SerialName("timestampValue")
    val timestampValue: String?
)

@Serializable
data class ReadTime(
    @SerialName("integerValue")
    val integerValue: String?
)

@Serializable
data class ReadmeFile(
    @SerialName("stringValue")
    val stringValue: String?
)

@Serializable
data class Tags(
    @SerialName("arrayValue")
    val arrayValue: ArrayValue?
)

@Serializable
data class ArrayValue(
    @SerialName("values")
    val values: List<Value?>?
)

@Serializable
data class Value(
    @SerialName("stringValue")
    val stringValue: String?
)


@Serializable
data class TinyImg(
    @SerialName("stringValue")
    val stringValue: String?
)

@Serializable
data class Title(
    @SerialName("stringValue")
    val stringValue: String?
)

@Serializable
data class UrlStr(
    @SerialName("stringValue")
    val stringValue: String?
)
        
    
