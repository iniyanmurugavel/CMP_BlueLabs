package com.neilsayok.bluelabs.data.bloglist

import com.neilsayok.bluelabs.data.documents.AuthorFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias IndexResult = List<IndexResultItem>

@Serializable
data class IndexResultItem(
    @SerialName("author_name") val authorName: String? = null,
    @SerialName("author_uid") val authorUid: String? = null,
    @SerialName("body") val body: String? = null,
    @SerialName("tags") val tags: List<String?>? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("blog") val blog: BlogLoadedFields? = null,
    @SerialName("author") val author: AuthorFields? = null
){
    fun getMap() : Map<String, String> {
        return mapOf(
            "author_name" to (authorName ?: ""),
            "author_uid" to (authorUid ?: ""),
            "body" to (body ?: ""),
            "tags" to (tags?.joinToString(",") ?: ""),
            "title" to (title ?: ""),
            "url" to (url ?: "")
        )
    }


}


fun Map<String, String>.getIndexResultObject(
    blogList: List<BlogLoadedFields?>,
    authorList: List<Document<AuthorFields>?>?
): IndexResultItem {

    val blog = blogList.firstOrNull { this["url"]?.contains( it?.urlStr?.stringValue?:"!@#$%%^^&*&*())_++" ) == true}
    val author = authorList?.firstOrNull { this["author_uid"] == it?.fields?.uid?.stringValue }


    return IndexResultItem(
        authorName = this["author_name"],
        authorUid =  this["author_uid"],
        body = this["body"],
        tags =  this["tags"]?.split(","),
        title =  this["title"],
        url =  this["url"],
        blog = blog,
        author = author?.fields
    )
}