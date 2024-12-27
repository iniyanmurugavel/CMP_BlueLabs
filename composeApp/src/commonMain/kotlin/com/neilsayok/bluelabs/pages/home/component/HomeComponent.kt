package com.neilsayok.bluelabs.pages.home.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class HomeComponent(
    componentContext: ComponentContext,
    private val navigateToBlogScreen: (String) -> Unit
) : ComponentContext by componentContext {

    private val _id = MutableValue("blog-page")
    val id: Value<String> = _id


    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.ClickButton -> navigateToBlogScreen(id.value)
        }

    }


}


sealed interface HomeScreenEvent {
    data object ClickButton : HomeScreenEvent
}