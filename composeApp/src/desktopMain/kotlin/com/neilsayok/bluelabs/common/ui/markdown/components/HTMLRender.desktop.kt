package com.neilsayok.bluelabs.common.ui.markdown.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import javax.swing.JEditorPane

@Composable
actual fun HTMLRender(html: String, modifier: Modifier) {
    SwingPanel(
        modifier = modifier,
        factory = {
            JEditorPane("text/html", html).apply {
                isEditable = false
                contentType = "text/html"
            }
        }
    )
}