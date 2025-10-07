package com.neilsayok.bluelabs.common.ui.markdown.components

import com.mikepenz.markdown.compose.components.MarkdownComponent

/**
 * Markdown table component using HTML-style implementation.
 * See MarkdownTable.kt for implementation details.
 */
val mdTable: MarkdownComponent = {
    HtmlStyleMarkdownTable(it.content, it.node, style = it.typography.table)
}