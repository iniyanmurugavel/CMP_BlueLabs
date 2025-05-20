package com.neilsayok.bluelabs.data.bloglist

import com.neilsayok.bluelabs.data.documents.AuthorFields
import com.neilsayok.bluelabs.data.documents.BigImg
import com.neilsayok.bluelabs.data.documents.Bio
import com.neilsayok.bluelabs.data.documents.BlogFields
import com.neilsayok.bluelabs.data.documents.Description
import com.neilsayok.bluelabs.data.documents.GenreFields
import com.neilsayok.bluelabs.data.documents.ImgUrl
import com.neilsayok.bluelabs.data.documents.IsPublished
import com.neilsayok.bluelabs.data.documents.Joined
import com.neilsayok.bluelabs.data.documents.Location
import com.neilsayok.bluelabs.data.documents.Name
import com.neilsayok.bluelabs.data.documents.Pass
import com.neilsayok.bluelabs.data.documents.Posted
import com.neilsayok.bluelabs.data.documents.ProfileFields
import com.neilsayok.bluelabs.data.documents.ReadTime
import com.neilsayok.bluelabs.data.documents.ReadmeFile
import com.neilsayok.bluelabs.data.documents.Tags
import com.neilsayok.bluelabs.data.documents.TinyImg
import com.neilsayok.bluelabs.data.documents.Title
import com.neilsayok.bluelabs.data.documents.Uid
import com.neilsayok.bluelabs.data.documents.UrlStr
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class BlogLoadedData(
    @SerialName("documents")
    val documents: List<Document<BlogLoadedFields>?>? = emptyList()
)


    @Serializable
data class BlogLoadedFields(
    @SerialName("author") val author: AuthorLoadedData? = null,
    @SerialName("big_img") val bigImg: BigImg? = null,
    @SerialName("genre") val genre: GenreFields? = null,
    @SerialName("isPublished") val isPublished: IsPublished? = null,
    @SerialName("posted") val posted: Posted? = null,
    @SerialName("read_time") val readTime: ReadTime? = null,
    @SerialName("readme_file") val readmeFile: ReadmeFile? = null,
    @SerialName("tags") val tags: Tags? = null,
    @SerialName("tiny_img") val tinyImg: TinyImg? = null,
    @SerialName("title") val title: Title? = null,
    @SerialName("url_str") val urlStr: UrlStr? = null
)


@Serializable
data class AuthorLoadedData(
    @SerialName("bio") val bio: Bio?,
    @SerialName("description") val description: Description?,
    @SerialName("img_url") val imgUrl: ImgUrl?,
    @SerialName("joined") val joined: Joined?,
    @SerialName("location") val location: Location?,
    @SerialName("name") val name: Name?,
    @SerialName("pass") val pass: Pass?,
    @SerialName("profiles") val profiles: ProfileFields?,
    @SerialName("uid") val uid: Uid?
)

fun AuthorFields.toAuthorLoadedDate(profiles: ProfileFields?) =
    AuthorLoadedData(
        bio = bio,
        description = description,
        imgUrl = imgUrl,
        joined = joined,
        location = location,
        name = name,
        pass = pass,
        profiles = profiles,
        uid = uid
    )


fun BlogFields.toBlogLoadedDate(author: AuthorFields?, genre: GenreFields?, profiles: ProfileFields?) =
    BlogLoadedFields(
        author = author?.toAuthorLoadedDate(profiles),
        bigImg = bigImg,
        genre = genre,
        isPublished = isPublished,
        posted = posted,
        readTime = readTime,
        readmeFile = readmeFile,
        tags = tags,
        tinyImg = tinyImg,
        title = title,
        urlStr = urlStr
    )




