package com.neilsayok.bluelabs

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.webhistory.withWebHistory
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.neilsayok.bluelabs.navigation.RootComponent
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class, ExperimentalDecomposeApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val lifecycle = LifecycleRegistry()
        val root = withWebHistory{stateKeeper, deepLink ->
            RootComponent(
                DefaultComponentContext(lifecycle, stateKeeper),
                deepLinkUrl = deepLink
            )
        }
                App(root)
    }

}

