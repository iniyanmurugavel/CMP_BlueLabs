package com.neilsayok.bluelabs.common.ui.markdown.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * HTML-style markdown table implementation.
 *
 * Properties:
 * - Wraps to content width by default
 * - Equal column widths and row heights
 * - Text wraps within cells for large content
 * - Border-collapse style with 1dp borders
 * - Alternating row colors for readability
 * - 8dp padding (HTML standard)
 */
@Composable
fun HtmlStyleMarkdownTable(
    content: String,
    node: ASTNode,
    style: TextStyle,
    annotatorSettings: AnnotatorSettings = annotatorSettings(),
) {
    val markdownComponents = LocalMarkdownComponents.current

    val headerNode = node.findChildOfType(HEADER)
    val dataRows = node.children.filter { it.type == ROW }
    val columnCount = headerNode?.children?.count { it.type == CELL } ?: 0

    if (columnCount == 0) return

    Column(
        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        // Header row
        headerNode?.let { header ->
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                header.children.filter { it.type == CELL }.forEach { cell ->
                    TableCell(
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

        // Data rows with alternating colors
        dataRows.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .background(
                        if (rowIndex % 2 == 0) Color.Transparent
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
            ) {
                row.children.filter { it.type == CELL }.forEach { cell ->
                    TableCell(
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
private fun RowScope.TableCell(
    content: String,
    cell: ASTNode,
    style: TextStyle,
    textColor: Color,
    annotatorSettings: AnnotatorSettings,
    markdownComponents: MarkdownComponents,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .border(1.dp, MaterialTheme.colorScheme.outline)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (cell.children.any { it.type == IMAGE }) {
            MarkdownElement(
                node = cell,
                components = markdownComponents,
                content = content,
                includeSpacer = false
            )
        } else {
            MarkdownBasicText(
                text = content.buildMarkdownAnnotatedString(
                    textNode = cell,
                    style = style,
                    annotatorSettings = annotatorSettings,
                ),
                style = style,
                color = textColor,
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Visible,
            )
        }
    }
}
