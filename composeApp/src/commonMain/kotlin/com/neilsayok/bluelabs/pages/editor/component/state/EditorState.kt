package com.neilsayok.bluelabs.pages.editor.component.state

import com.neilsayok.bluelabs.data.bloglist.FirebaseResponse
import com.neilsayok.bluelabs.data.documents.AuthorFields
import com.neilsayok.bluelabs.data.documents.GenreFields
import com.neilsayok.bluelabs.domain.util.Response

data class EditorState(
    val isLoading: Boolean = false,
    val genreResponse: Response<FirebaseResponse<GenreFields>> = Response.None,
    val authorResponse: Response<FirebaseResponse<AuthorFields>> = Response.None,
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val formData: BlogFormData = BlogFormData(),
    val validationErrors: Set<ValidationError> = emptySet()
)

data class BlogFormData(
    val selectedGenre: String = "",
    val tinyImageUrl: String = "",
    val bigImageUrl: String = "",
    val title: String = "",
    val tags: List<String> = emptyList(),
    val mdFileName: String = "",
    val markdownContent: String = "",
    val selectedAuthor: String = "",
    val isPublished: Boolean = true
)

enum class ValidationError {
    GENRE_EMPTY,
    TINY_IMAGE_URL_EMPTY,
    BIG_IMAGE_URL_EMPTY,
    TITLE_EMPTY,
    TITLE_TOO_LONG,
    TAGS_EMPTY,
    TAGS_TOO_MANY,
    MD_FILE_EMPTY,
    MARKDOWN_CONTENT_EMPTY,
    AUTHOR_EMPTY
}