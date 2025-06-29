package com.neilsayok.bluelabs.common.ui.markdown.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import com.mikepenz.markdown.annotator.AnnotatorSettings
import com.mikepenz.markdown.annotator.annotatorSettings
import com.mikepenz.markdown.annotator.buildMarkdownAnnotatedString
import com.mikepenz.markdown.compose.LocalMarkdownColors
import com.mikepenz.markdown.compose.LocalMarkdownComponents
import com.mikepenz.markdown.compose.LocalMarkdownDimens
import com.mikepenz.markdown.compose.MarkdownElement
import com.mikepenz.markdown.compose.components.MarkdownComponent
import com.mikepenz.markdown.compose.elements.MarkdownDivider
import com.mikepenz.markdown.compose.elements.MarkdownTableBasicText
import com.mikepenz.markdown.compose.elements.material.MarkdownBasicText
import org.intellij.markdown.MarkdownElementTypes.IMAGE
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.findChildOfType
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes.HEADER
import org.intellij.markdown.flavours.gfm.GFMElementTypes.ROW
import org.intellij.markdown.flavours.gfm.GFMTokenTypes.CELL
import org.intellij.markdown.flavours.gfm.GFMTokenTypes.TABLE_SEPARATOR

val mdTable: MarkdownComponent = {
    MdTable(it.content, it.node, style = it.typography.table)
}



@Composable
fun MdTable(
    content: String,
    node: ASTNode,
    style: TextStyle,
    annotatorSettings: AnnotatorSettings = annotatorSettings(),
    headerBlock: @Composable (String, ASTNode, Dp, TextStyle) -> Unit = { content, header, tableWidth, style ->
        MDTableHeader(
            content = content,
            header = header,
            tableWidth = tableWidth,
            style = style,
            annotatorSettings = annotatorSettings,
        )
    },
    rowBlock: @Composable (String, ASTNode, Dp, TextStyle, Int) -> Unit = { content, header, tableWidth, style, index ->
        MDTableRow(
            content = content,
            header = header,
            tableWidth = tableWidth,
            style = style,
            annotatorSettings = annotatorSettings,
            index = index
        )
    },
) {
    val tableMaxWidth = LocalMarkdownDimens.current.tableMaxWidth
    val tableCellWidth = LocalMarkdownDimens.current.tableCellWidth
    val tableCornerSize = LocalMarkdownDimens.current.tableCornerSize

    var index = 0
    var noHeader = false




    val columnsCount =
        remember(node) { node.findChildOfType(HEADER)?.children?.count { it.type == CELL } ?: 0 }
    val tableWidth = columnsCount * tableCellWidth

    val backgroundCodeColor = LocalMarkdownColors.current.tableBackground

//    val nonTextCell = node.children.flatMap { cell ->
//        println(cell.children.)
//        cell.children.filter { it.type == IMAGE }
//    }.toMutableList()







    BoxWithConstraints(
        modifier = Modifier.background(backgroundCodeColor, RoundedCornerShape(tableCornerSize))
            .widthIn(max = tableMaxWidth)
    ) {
        val scrollable = maxWidth <= tableWidth
        Column(
            modifier = if (scrollable) {
                Modifier.horizontalScroll(rememberScrollState()).requiredWidth(tableWidth)
            } else Modifier.fillMaxWidth()
        ) {

            node.children.forEach { node ->

                when (node.type) {
                    HEADER -> {
                        noHeader = node.getTextInNode(content).replace(Regex("[| ]"), "").isBlank()
                        if (!noHeader) headerBlock(content, node, tableWidth, style)
                    }

                    ROW -> {
                        rowBlock(content, node, tableWidth, style, index)
                        index++
                    }

                    TABLE_SEPARATOR -> if (noHeader) MarkdownDivider()
                }
            }
        }
    }
}


@Composable
fun MDTableHeader(
    content: String,
    header: ASTNode,
    tableWidth: Dp,
    style: TextStyle,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    annotatorSettings: AnnotatorSettings = annotatorSettings(),
) {
    val markdownComponents = LocalMarkdownComponents.current
    val tableCellPadding = LocalMarkdownDimens.current.tableCellPadding
    Row(
        verticalAlignment = verticalAlignment,
        modifier = Modifier.widthIn(tableWidth).height(IntrinsicSize.Max)
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        header.children.filter { it.type == CELL }.forEach { cell ->
            Column(
                modifier = Modifier.padding(tableCellPadding).weight(1f),
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
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = maxLines,
                        overflow = overflow,
                    )


                }
            }
        }
    }
}

@Composable
fun MDTableRow(
    content: String,
    header: ASTNode,
    tableWidth: Dp,
    style: TextStyle,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    annotatorSettings: AnnotatorSettings = annotatorSettings(),
    index: Int
) {
    val markdownComponents = LocalMarkdownComponents.current
    val tableCellPadding = LocalMarkdownDimens.current.tableCellPadding
    Row(
        verticalAlignment = verticalAlignment,
        modifier = Modifier.widthIn(tableWidth)
            .background(color = if (index % 2 == 0) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer)


    ) {
        header.children.filter { it.type == CELL }.forEach { cell ->
            Column(
                modifier = Modifier.padding(tableCellPadding).weight(1f),
            ) {
                if (cell.children.any { it.type == IMAGE }) {
                    MarkdownElement(
                        node = cell,
                        components = markdownComponents,
                        content = content,
                        includeSpacer = false
                    )
                } else {
                    MarkdownTableBasicText(
                        content = content,
                        cell = cell,
                        style = style,
                        maxLines = maxLines,
                        overflow = overflow,
                        annotatorSettings = annotatorSettings
                    )
                }
            }
        }
    }
}


fun ASTNode.withAllChildren(): List<ASTNode> {
    val result = mutableListOf<ASTNode>()

    fun collect(node: ASTNode) {
        result.add(node)
        node.children.forEach { collect(it) }
    }

    collect(this)
    return result
}