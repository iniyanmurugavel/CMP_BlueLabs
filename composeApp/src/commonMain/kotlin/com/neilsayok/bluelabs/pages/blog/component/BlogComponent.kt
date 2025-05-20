package com.neilsayok.bluelabs.pages.blog.component

import com.arkivanov.decompose.ComponentContext
import com.neilsayok.bluelabs.data.bloglist.BlogLoadedFields

class BlogComponent(
    val blog: BlogLoadedFields,
    private val navigateBack: () -> Unit,
    componentContext: ComponentContext,
) : ComponentContext by componentContext {


    fun goBack() {
        navigateBack()
    }
}