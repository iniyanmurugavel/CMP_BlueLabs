package com.neilsayok.bluelabs

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.neilsayok.bluelabs.navigation.RootComponent
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val root = remember {
            RootComponent(DefaultComponentContext(LifecycleRegistry()))
        }
        App(root)
    }
}