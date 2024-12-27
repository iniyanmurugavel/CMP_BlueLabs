package com.neilsayok.bluelabs.pages.blog.component

import com.arkivanov.decompose.ComponentContext

class BlogComponent(
    val id: String,
    private val navigateBack: () -> Unit,
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    fun goBack() {
        navigateBack()
    }
}