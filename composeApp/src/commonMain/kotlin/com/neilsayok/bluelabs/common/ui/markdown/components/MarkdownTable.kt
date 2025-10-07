package com.neilsayok.bluelabs.common.ui.markdown.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.annotator.AnnotatorSettings
import com.mikepenz.markdown.annotator.annotatorSettings
import com.mikepenz.markdown.annotator.buildMarkdownAnnotatedString
import com.mikepenz.markdown.compose.LocalMarkdownComponents
import com.mikepenz.markdown.compose.MarkdownElement
import com.mikepenz.markdown.compose.components.MarkdownComponent
import com.mikepenz.markdown.compose.components.MarkdownComponents
import com.mikepenz.markdown.compose.elements.material.MarkdownBasicText
import org.intellij.markdown.MarkdownElementTypes.IMAGE
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.findChildOfType
import org.intellij.markdown.flavours.gfm.GFMElementTypes.HEADER
import org.intellij.markdown.flavours.gfm.GFMElementTypes.ROW
import org.intellij.markdown.flavours.gfm.GFMTokenTypes.CELL

val htmlStyleTable: MarkdownComponent = {
    HtmlStyleMarkdownTable(it.content, it.node, style = it.typography.table)
}

/**
 * HTML-style markdown table with properties:
 * 1. Wraps to content by default
 * 2. Equal column widths and equal row heights
 * 3. Doesn't exceed screen width (no horizontal scroll - text wraps instead)
 * 4. Text wraps within cells for large content
 * 5. Border-collapse style with alternating row colors
 */
@Composable
fun HtmlStyleMarkdownTable(
    content: String,
    node: ASTNode,
    style: TextStyle,
    annotatorSettings: AnnotatorSettings = annotatorSettings(),
) {
    val markdownComponents = LocalMarkdownComponents.current

    // Get header and data rows
    val headerNode = node.findChildOfType(HEADER)
    val dataRows = node.children.filter { it.type == ROW }

    // Calculate column count
    val columnCount = headerNode?.children?.count { it.type == CELL } ?: 0
    if (columnCount == 0) return

    // Table wraps to content, constrained by parent width
    Column(
        modifier = Modifier
//            .fillMaxWidth() // Max width is parent, prevents overflow
            .border(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        // Render header if present
        headerNode?.let { header ->
            Row(
                modifier = Modifier
//                    .fillMaxWidth()
                    .height(IntrinsicSize.Min) // All cells in row have same height
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                header.children.filter { it.type == CELL }.forEach { cell ->
                    HtmlTableCell(
                        content = content,
                        cell = cell,
                        style = style.copy(fontWeight = FontWeight.Bold),
                        textColor = MaterialTheme.colorScheme.onSurface,
                        annotatorSettings = annotatorSettings,
                        markdownComponents = markdownComponents
                    )
                }
            }
        }

        // Render data rows with alternating colors
        dataRows.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier
//                    .fillMaxWidth()
                    .height(IntrinsicSize.Min) // All cells in row have same height
                    .background(
                        if (rowIndex % 2 == 0)
                            Color.Transparent
                        else
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
            ) {
                row.children.filter { it.type == CELL }.forEach { cell ->
                    HtmlTableCell(
                        content = content,
                        cell = cell,
                        style = style,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        annotatorSettings = annotatorSettings,
                        markdownComponents = markdownComponents
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.HtmlTableCell(
    content: String,
    cell: ASTNode,
    style: TextStyle,
    textColor: Color,
    annotatorSettings: AnnotatorSettings,
    markdownComponents: MarkdownComponents,
) {
    Box(
        modifier = Modifier
            .weight(1f) // Equal width for all columns
            .fillMaxHeight() // Equal height for all cells in the row
            .border(1.dp, MaterialTheme.colorScheme.outline)
            .padding(8.dp), // HTML default padding (like td, th)
        contentAlignment = Alignment.CenterStart
    ) {
        // Check if cell contains images or complex content
        if (cell.children.any { it.type == IMAGE }) {
            MarkdownElement(
                node = cell,
                components = markdownComponents,
                content = content,
                includeSpacer = false
            )
        } else {
            // Render text with wrapping for large content
            MarkdownBasicText(
                text = content.buildMarkdownAnnotatedString(
                    textNode = cell,
                    style = style,
                    annotatorSettings = annotatorSettings,
                ),
                style = style,
                color = textColor,
                maxLines = Int.MAX_VALUE, // Allow unlimited line wrapping
                overflow = TextOverflow.Visible,
            )
        }
    }
}
