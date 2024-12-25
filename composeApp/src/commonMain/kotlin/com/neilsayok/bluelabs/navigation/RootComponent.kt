package com.neilsayok.bluelabs.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.neilsayok.bluelabs.ui.blog.BlogComponent
import com.neilsayok.bluelabs.ui.home.HomeComponent
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuratuion>()


    val childStack: Value<ChildStack<Configuratuion, Child>> = childStack(
        source = navigation,
        serializer = Configuratuion.serializer(),
        initialConfiguration = Configuratuion.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )


    private fun createChild(
        config: Configuratuion,
        context: ComponentContext
    ): Child {
        return when (config) {
            is Configuratuion.HomeScreen -> Child.Home(HomeComponent(
                componentContext = context,
                navigateToBlogScreen = { id -> navigation.pushNew(Configuratuion.BlogScreen(id)) }
            ))

            is Configuratuion.BlogScreen -> Child.Blog(BlogComponent(
                id = config.id,
                componentContext = context,
                navigateBack = { navigation.pop() }
            ))
        }

    }


    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class Blog(val component: BlogComponent) : Child()
    }

    @Serializable
    sealed class Configuratuion {
        @Serializable
        data object HomeScreen : Configuratuion()
        @Serializable
        data class BlogScreen(val id: String) : Configuratuion()
    }
}