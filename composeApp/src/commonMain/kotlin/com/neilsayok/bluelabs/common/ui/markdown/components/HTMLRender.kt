package com.neilsayok.bluelabs.common.ui.markdown.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.markdown.compose.components.CustomMarkdownComponent
import org.intellij.markdown.MarkdownElementTypes


@Composable
expect fun HTMLRender(html: String, modifier: Modifier = Modifier)



val customRenderer: CustomMarkdownComponent = { elementType, model ->
    if (elementType == MarkdownElementTypes.HTML_BLOCK) {
        HTMLRender(model.content, Modifier.wrapContentWidth())
    }
}