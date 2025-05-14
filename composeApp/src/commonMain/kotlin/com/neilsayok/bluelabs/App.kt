package com.neilsayok.bluelabs

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.neilsayok.bluelabs.di.initKoin
import com.neilsayok.bluelabs.navigation.RootComponent
import com.neilsayok.bluelabs.pages.blog.screen.BlogScreen
import com.neilsayok.bluelabs.pages.editor.screen.EditorScreen
import com.neilsayok.bluelabs.pages.editor.screen.PageNotFoundScreen
import com.neilsayok.bluelabs.pages.home.screen.HomeScreen
import com.neilsayok.bluelabs.pages.indexer.screen.IndexerScreen
import com.neilsayok.bluelabs.pages.portfolio.screen.PortfolioScreen
import com.neilsayok.bluelabs.pages.privacy.screen.PrivacyPolicyScreen
import com.neilsayok.bluelabs.pages.search.screen.SearchScreen
import com.neilsayok.bluelabs.theme.BlueLabsTheme
import com.neilsayok.bluelabs.util.isAndroid
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun App(root: RootComponent) {

    if (!isAndroid())
        initKoin()

    SelectionContainer {
        BlueLabsTheme(darkTheme = false) {
            Children(
                stack = root.stack,
                modifier = Modifier,
                animation = stackAnimation(slide()),
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.Home -> HomeScreen(instance.component)
                    is RootComponent.Child.Blog -> BlogScreen(instance.component)
                    is RootComponent.Child.Editor -> EditorScreen(instance.component)
                    is RootComponent.Child.Indexer -> IndexerScreen(instance.component)
                    is RootComponent.Child.Portfolio -> PortfolioScreen(instance.component)
                    is RootComponent.Child.PrivacyPolicy -> PrivacyPolicyScreen(instance.component)
                    is RootComponent.Child.Search -> SearchScreen(instance.component)
                    is RootComponent.Child.PageNotFound -> PageNotFoundScreen(instance.component)
                }
            }
        }
    }

}

