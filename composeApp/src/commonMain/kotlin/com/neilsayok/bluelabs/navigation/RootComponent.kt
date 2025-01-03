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
import com.neilsayok.bluelabs.common.constants.BLOG_PAGE
import com.neilsayok.bluelabs.common.constants.EDITOR_PAGE
import com.neilsayok.bluelabs.common.constants.INDEXER_PAGE
import com.neilsayok.bluelabs.common.constants.PAGE_NOT_FOUND_PAGE
import com.neilsayok.bluelabs.common.constants.PORTFOLIO_PAGE
import com.neilsayok.bluelabs.common.constants.PRIVACY_POLICY_PAGE
import com.neilsayok.bluelabs.common.constants.SEARCH_PAGE
import com.neilsayok.bluelabs.pages.blog.component.BlogComponent
import com.neilsayok.bluelabs.pages.editor.component.EditorComponent
import com.neilsayok.bluelabs.pages.editor.component.PageNotFoundComponent
import com.neilsayok.bluelabs.pages.home.component.HomeComponent
import com.neilsayok.bluelabs.pages.indexer.component.IndexerComponent
import com.neilsayok.bluelabs.pages.portfolio.component.PortfolioComponent
import com.neilsayok.bluelabs.pages.privacy.component.PrivacyPolicyComponent
import com.neilsayok.bluelabs.pages.search.component.SearchComponent
import kotlinx.serialization.Serializable


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
                BlogComponent(id = config.id,
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

            Configuration.PageNotFoundScreen -> Child.PageNotFound(
                PageNotFoundComponent(
                    componentContext = context
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
        class PageNotFound(val component: PageNotFoundComponent) : Child()

    }

    @Serializable
    sealed class Configuration(val path: String) {
        @Serializable
        data object HomeScreen : Configuration("/")

        @Serializable
        data object EditorScreen : Configuration("/$EDITOR_PAGE")

        @Serializable
        data object IndexerScreen : Configuration("/$INDEXER_PAGE")

        @Serializable
        data object PrivacyPolicyScreen : Configuration("/$PRIVACY_POLICY_PAGE")

        @Serializable
        data object PortfolioScreen : Configuration("/$PORTFOLIO_PAGE")

        @Serializable
        data object PageNotFoundScreen : Configuration("/$PAGE_NOT_FOUND_PAGE")


        @Serializable
        data class BlogScreen(val id: String) : Configuration("/$BLOG_PAGE/$id")

        @Serializable
        data class SearchScreen(val key: String) : Configuration("/$SEARCH_PAGE/$key")


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

    private fun parseDeepLink(url: String): Configuration? {
        val pathSegments = url.split("/").filter { it.isNotEmpty() }.drop(2)
//        println("URL = $url")
//        println("pathSegments = $pathSegments")

        return when {
            pathSegments.isEmpty() -> Configuration.HomeScreen
            pathSegments.contains(EDITOR_PAGE) -> Configuration.EditorScreen
            pathSegments.contains(INDEXER_PAGE) -> Configuration.IndexerScreen
            pathSegments.contains(PRIVACY_POLICY_PAGE) -> Configuration.PrivacyPolicyScreen
            pathSegments.contains(PORTFOLIO_PAGE) -> Configuration.PortfolioScreen

            pathSegments.size == 2 && pathSegments[0] == BLOG_PAGE -> {
                val id = pathSegments[1]
                Configuration.BlogScreen(id)
            }

            pathSegments.size == 2 && pathSegments[0] == SEARCH_PAGE -> {
                val key = pathSegments[1]
                Configuration.SearchScreen(key)
            }

            else -> Configuration.PageNotFoundScreen
        }

    }


}





