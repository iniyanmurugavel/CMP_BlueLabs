package com.neilsayok.bluelabs.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.childStackWebNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.webhistory.WebNavigation
import com.arkivanov.decompose.router.webhistory.WebNavigationOwner
import com.arkivanov.decompose.value.Value
import com.neilsayok.bluelabs.pages.blog.component.BlogComponent
import com.neilsayok.bluelabs.pages.editor.component.EditorComponent
import com.neilsayok.bluelabs.pages.home.component.HomeComponent
import com.neilsayok.bluelabs.pages.indexer.component.IndexerComponent
import com.neilsayok.bluelabs.pages.portfolio.component.PortfolioComponent
import com.neilsayok.bluelabs.pages.privacy.component.PrivacyPolicyComponent
import com.neilsayok.bluelabs.pages.search.component.SearchComponent
import kotlinx.serialization.Serializable
import org.intellij.markdown.html.URI

@OptIn(ExperimentalDecomposeApi::class)
interface MyStackComponent : WebNavigationOwner {
    val stack: Value<ChildStack<*, RootComponent.Child>>
}

@OptIn(ExperimentalDecomposeApi::class)
class RootComponent(
    componentContext: ComponentContext,
    deepLinkUrl: String? = null,
) : MyStackComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    private val _stack: Value<ChildStack<Configuration, Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialStack = { initialConfig(deepLinkUrl) },
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val stack: Value<ChildStack<*, Child>> = _stack

    override val webNavigation: WebNavigation<*> = childStackWebNavigation(
        navigator = navigation,
        stack = _stack,
        serializer = Configuration.serializer(),
        pathMapper = { child -> child.configuration.path },
        childSelector = { child -> child.instance as? WebNavigationOwner },
    )

    private fun createChild(
        config: Configuration, context: ComponentContext
    ): Child {
        return when (config) {
            is Configuration.HomeScreen -> Child.Home(
                HomeComponent(componentContext = context,
                    navigateToBlogScreen = { id -> navigation.pushNew(Configuration.BlogScreen(id)) })
            )

            is Configuration.BlogScreen -> Child.Blog(
                BlogComponent(
                    id = config.id,
                    componentContext = context,
                    navigateBack = { navigation.pop() })
            )

            Configuration.EditorScreen -> Child.Editor(EditorComponent(componentContext = context))
            Configuration.IndexerScreen -> Child.Indexer(IndexerComponent(componentContext = context))
            Configuration.PortfolioScreen -> Child.Portfolio(PortfolioComponent(componentContext = context))
            Configuration.PrivacyPolicyScreen -> Child.PrivacyPolicy(
                PrivacyPolicyComponent(
                    componentContext = context
                )
            )

            is Configuration.SearchScreen -> Child.Search(
                SearchComponent(
                    componentContext = context, key = config.key
                )
            )
        }

    }

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class Blog(val component: BlogComponent) : Child()
        class Editor(val component: EditorComponent) : Child()
        class Indexer(val component: IndexerComponent) : Child()
        class Portfolio(val component: PortfolioComponent) : Child()
        class PrivacyPolicy(val component: PrivacyPolicyComponent) : Child()
        class Search(val component: SearchComponent) : Child()

    }

    @Serializable
    sealed class Configuration(val path: String) {
        @Serializable
        data object HomeScreen : Configuration("/")

        @Serializable
        data object EditorScreen : Configuration("/editor")

        @Serializable
        data object IndexerScreen : Configuration("/indexer")

        @Serializable
        data object PrivacyPolicyScreen : Configuration("/privacy-policy")

        @Serializable
        data object PortfolioScreen : Configuration("/portfolio")


        @Serializable
        data class BlogScreen(val id: String) : Configuration("/blog/$id")

        @Serializable
        data class SearchScreen(val key: String) : Configuration("/search/$key")


    }

    private fun initialConfig(deepLinkUrl: String?): List<Configuration> {
        // Parse the deep link and initialize navigation state
        val deepLink = deepLinkUrl?.let { parseDeepLink(it) }
        return if (deepLink != null) {
            listOf(deepLink)
        } else {
            listOf(Configuration.HomeScreen)
        }
    }

    fun parseDeepLink(url: String): Configuration? {
        val uri = URI(url)
        val pathSegments = uri.path().split("/").filter { it.isNotEmpty() }

        return when {
            pathSegments.isEmpty() -> Configuration.HomeScreen

            pathSegments.contains("editor") -> Configuration.EditorScreen
            pathSegments.contains("indexer") -> Configuration.IndexerScreen
            pathSegments.contains("privacy-policy") -> Configuration.PrivacyPolicyScreen
            pathSegments.contains("portfolio") -> Configuration.PortfolioScreen

            pathSegments.size == 2 && pathSegments[0] == "blog" -> {
                val id = pathSegments[1]
                Configuration.BlogScreen(id)
            }

            pathSegments.size == 2 && pathSegments[0] == "search" -> {
                val key = pathSegments[1]
                Configuration.SearchScreen(key)
            }

            else -> null
        }
    }


}





