package com.neilsayok.bluelabs

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.neilsayok.bluelabs.di.initKoin
import com.neilsayok.bluelabs.navigation.RootComponent

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "BlueLabs CMP",
        ) {
            val root = remember {
                RootComponent(DefaultComponentContext(LifecycleRegistry()))
            }
            App(root)
        }
    }
}