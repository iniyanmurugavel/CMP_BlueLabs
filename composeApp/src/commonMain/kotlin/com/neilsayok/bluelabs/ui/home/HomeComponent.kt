package com.neilsayok.bluelabs.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlin.random.Random

class HomeComponent(
    componentContext: ComponentContext,
    private val navigateToBlogScreen: (String) -> Unit
) : ComponentContext by componentContext {

    private val _id = MutableValue(Random.nextInt().toString())
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