package com.neilsayok.bluelabs.common.ui.markdown.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.WebKit.WKWebView

@Composable
actual fun HTMLRender(html: String, modifier: Modifier) {
    UIKitView(factory = {
        val webView = WKWebView()
        webView.loadHTMLString(html, baseURL = null)
        webView
    }, modifier = modifier)

}