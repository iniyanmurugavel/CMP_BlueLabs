package com.neilsayok.bluelabs.ui.blog

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