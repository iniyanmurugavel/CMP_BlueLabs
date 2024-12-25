package com.neilsayok.bluelabs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.neilsayok.bluelabs.di.initKoin
import com.neilsayok.bluelabs.navigation.RootComponent
import com.neilsayok.bluelabs.ui.blog.screen.BlogScreen
import com.neilsayok.bluelabs.ui.empty.EmptyScreen
import com.neilsayok.bluelabs.ui.home.screen.HomeScreen
import com.neilsayok.bluelabs.util.isAndroid
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun App(root: RootComponent) {

    if (!isAndroid())
        initKoin()

    val childStack by root.childStack.subscribeAsState()

    MaterialTheme {
        Children(
            stack = childStack,
            modifier = Modifier,
            animation = stackAnimation(slide()),
        ){child ->
            when(val instance = child.instance){
                is RootComponent.Child.Home -> HomeScreen(instance.component)
                is RootComponent.Child.Blog -> BlogScreen(instance.component)
            }
        }
    }
}


fun test(){

}


//Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//    MarkdownHandler()
//
//}

