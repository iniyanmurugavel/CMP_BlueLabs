package com.neilsayok.bluelabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.neilsayok.bluelabs.common.ui.appbar.MainAppBar
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
import com.russhwolf.settings.Settings
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun App(root: RootComponent) {



    var isDark by remember { mutableStateOf(Settings().getBoolean("isDark", false)) }

    //Settings().putBoolean("isDark", false)

    BlueLabsTheme(darkTheme = isDark) {
        Scaffold(
            topBar = {
                MainAppBar(isDark) {
                    //delay(400)
                    Settings().putBoolean("isDark", !isDark)
                    isDark = !isDark
                }
            }) { padding ->

            SelectionContainer {
                Children(
                    stack = root.stack,
                    modifier = Modifier.padding(padding),
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
}

