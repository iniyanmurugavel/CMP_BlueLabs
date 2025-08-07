package com.neilsayok.bluelabs.pages.indexer.component.state

import com.neilsayok.bluelabs.data.bloglist.FirebaseResponse
import com.neilsayok.bluelabs.data.documents.AuthorFields
import com.neilsayok.bluelabs.data.documents.BlogFields
import com.neilsayok.bluelabs.domain.util.Response

data class IndexerState(
    val isLoading: Boolean = false,
    val blogResponse: Response<FirebaseResponse<BlogFields>> = Response.None,
    val authorResponse: Response<FirebaseResponse<AuthorFields>> = Response.None,
    val error: String? = null
)