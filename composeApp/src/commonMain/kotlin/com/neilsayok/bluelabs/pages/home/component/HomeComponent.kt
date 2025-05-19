package com.neilsayok.bluelabs.pages.home.component

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.neilsayok.bluelabs.util.layoutType

class HomeComponent(
    componentContext: ComponentContext,
    private val navigateToBlogScreen: (String) -> Unit
) : ComponentContext by componentContext {

    private val _id = MutableValue("blog-page")
    val id: Value<String> = _id


    val containerPadding:Dp
        @Composable get()  = when(layoutType){
        NavigationSuiteType.NavigationBar -> 16.dp
        NavigationSuiteType.NavigationRail,
        NavigationSuiteType.NavigationDrawer -> 64.dp
        else -> 0.dp
    }

    val columnCount:Int
        @Composable get()  = when(layoutType){
        NavigationSuiteType.NavigationBar -> 1
        NavigationSuiteType.NavigationRail,
        NavigationSuiteType.NavigationDrawer -> 4
        else -> 0
    }




    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.ClickButton -> navigateToBlogScreen(id.value)
        }

    }









}


sealed interface HomeScreenEvent {
    data object ClickButton : HomeScreenEvent
}