package com.neilsayok.bluelabs.common.ui.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntSize
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement


@Composable
actual fun HTMLRender(
    html: String, modifier: Modifier
) {

    //TODO Fix this

//
//    // State to track the position and size of the Compose container
//    var containerSize by remember { mutableStateOf(IntSize.Zero) }
//    var containerPosition by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }
//
//    // Create a container for the HTML content
//    val htmlContainer = remember {
//        document.createElement("div").unsafeCast<HTMLDivElement>().apply {
//            style.apply {
//                position = "absolute"
//            }
//
//            className.let { this.className = it }
//        }
//    }
//
//    // Box composable to host our HTML content and measure its size/position
//    Box(
//        modifier = modifier
//            .onGloballyPositioned { coordinates ->
//                containerSize = coordinates.size
//                containerPosition = coordinates.positionInRoot()
//            }
//    )
//
//    // Update HTML content and position when relevant parameters change
//    LaunchedEffect(html, containerSize, containerPosition) {
//        // Update the HTML content
//        htmlContainer.innerHTML = html
//
//        // Update the container position and size to match the Compose layout
//        htmlContainer.style.apply {
//            left = "${containerPosition.x}px"
//            top = "${containerPosition.y}px"
//            width = "${containerSize.width}px"
//            height = "${containerSize.height}px"
//        }
//
//        // Ensure the container is in the DOM
//        if (htmlContainer.parentElement == null) {
//            document.body?.appendChild(htmlContainer)
//        }
//    }
//
//    // Cleanup the DOM element when the composable leaves composition
//    DisposableEffect(Unit) {
//        onDispose {
//            htmlContainer.parentElement?.removeChild(htmlContainer)
//        }
//    }
}

