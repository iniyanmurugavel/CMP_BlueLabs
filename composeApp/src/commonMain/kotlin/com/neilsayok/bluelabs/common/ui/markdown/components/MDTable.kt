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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.mikepenz.markdown.annotator.AnnotatorSettings
import com.mikepenz.markdown.annotator.annotatorSettings
import com.mikepenz.markdown.annotator.buildMarkdownAnnotatedString
import com.mikepenz.markdown.compose.LocalMarkdownColors
import com.mikepenz.markdown.compose.LocalMarkdownComponents
import com.mikepenz.markdown.compose.LocalMarkdownDimens
import com.mikepenz.markdown.compose.MarkdownElement
import com.mikepenz.markdown.compose.components.MarkdownComponent
import com.mikepenz.markdown.compose.components.MarkdownComponents
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
    EnhancedMdTable(it.content, it.node, style = it.typography.table)
}

@Composable
fun EnhancedMdTable(
    content: String,
    node: ASTNode,
    style: TextStyle,
    annotatorSettings: AnnotatorSettings = annotatorSettings(),
) {
    val markdownComponents = LocalMarkdownComponents.current
    
    // Get all rows (header + data rows)
    val headerNode = node.findChildOfType(HEADER)
    val dataRows = node.children.filter { it.type == ROW }
    
    // Calculate column count
    val columnCount = headerNode?.children?.count { it.type == CELL } ?: 0
    
    if (columnCount == 0) return
    
    // Collect all cells for measurement
    val allCells = mutableListOf<Triple<ASTNode, TextStyle, Color>>()
    
    // Add header cells
    headerNode?.let { header ->
        header.children.filter { it.type == CELL }.forEach { cell ->
            allCells.add(Triple(cell, style.copy(fontWeight = FontWeight.Bold), MaterialTheme.colorScheme.onSecondary))
        }
    }
    
    // Add data cells
    dataRows.forEach { row ->
        row.children.filter { it.type == CELL }.forEach { cell ->
            allCells.add(Triple(cell, style, MaterialTheme.colorScheme.onSurface))
        }
    }
    
    // Use SubcomposeLayout to measure all cells first, then render with uniform size
    SubcomposeLayout { constraints ->
        // Phase 1: Measure all cells to find the maximum size
        val cellMeasurables = allCells.map { (cell, cellStyle, textColor) ->
            subcompose("measure_${cell.hashCode()}") {
                CellContent(
                    content = content,
                    cell = cell,
                    style = cellStyle,
                    textColor = textColor,
                    annotatorSettings = annotatorSettings,
                    markdownComponents = markdownComponents
                )
            }.first()
        }
        
        val cellPlaceables = cellMeasurables.map { it.measure(Constraints()) }
        
        // Find the maximum width and height among all cells
        val maxCellWidth = cellPlaceables.maxOfOrNull { it.width } ?: 0
        val maxCellHeight = cellPlaceables.maxOfOrNull { it.height } ?: 0
        
        // Add padding and border to the cell size
        val cellWidth = maxCellWidth + 4.dp.roundToPx() + 2.dp.roundToPx() // 2dp padding + 1dp border each side
        val cellHeight = maxCellHeight + 4.dp.roundToPx() + 2.dp.roundToPx()
        
        // Phase 2: Render the actual table with uniform cell sizes
        val tableComposable = subcompose("table") {
            UniformSizeTable(
                content = content,
                node = node,
                style = style,
                cellWidth = cellWidth,
                cellHeight = cellHeight,
                annotatorSettings = annotatorSettings,
                markdownComponents = markdownComponents
            )
        }.first()
        
        val tablePlaceable = tableComposable.measure(constraints)
        
        layout(tablePlaceable.width, tablePlaceable.height) {
            tablePlaceable.place(0, 0)
        }
    }
}

@Composable
fun CellContent(
    content: String,
    cell: ASTNode,
    style: TextStyle,
    textColor: Color,
    annotatorSettings: AnnotatorSettings,
    markdownComponents: MarkdownComponents,
) {
    // Check if cell contains images or other complex content
    if (cell.children.any { it.type == IMAGE }) {
        // Render complex content (images, etc.)
        MarkdownElement(
            node = cell,
            components = markdownComponents,
            content = content,
            includeSpacer = false
        )
    } else {
        // Render text content
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

@Composable
fun UniformSizeTable(
    content: String,
    node: ASTNode,
    style: TextStyle,
    cellWidth: Int,
    cellHeight: Int,
    annotatorSettings: AnnotatorSettings,
    markdownComponents: MarkdownComponents,
) {
    val density = LocalDensity.current
    val cellWidthDp = with(density) { cellWidth.toDp() }
    val cellHeightDp = with(density) { cellHeight.toDp() }
    
    // Get all rows (header + data rows)
    val headerNode = node.findChildOfType(HEADER)
    val dataRows = node.children.filter { it.type == ROW }
    
    // Calculate column count
    val columnCount = headerNode?.children?.count { it.type == CELL } ?: 0
    
    if (columnCount == 0) return
    
    // Create table with uniform cell sizes
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            //.border(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        // Render header if present
        headerNode?.let { header ->
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .border(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                header.children.filter { it.type == CELL }.forEachIndexed { index, cell ->
                    UniformTableCell(
                        content = content,
                        cell = cell,
                        style = style.copy(fontWeight = FontWeight.Bold),
                        textColor = MaterialTheme.colorScheme.onSecondary,
                        cellWidth = cellWidthDp,
                        cellHeight = cellHeightDp,
                        columnIndex = index,
                        totalColumns = columnCount,
                        annotatorSettings = annotatorSettings,
                        markdownComponents = markdownComponents
                    )
                }
            }
        }
        
        // Render data rows
        dataRows.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(
                        if (rowIndex % 2 == 0) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.secondaryContainer
                    )
                    .border(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                row.children.filter { it.type == CELL }.forEachIndexed { columnIndex, cell ->
                    UniformTableCell(
                        content = content,
                        cell = cell,
                        style = style,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        cellWidth = cellWidthDp,
                        cellHeight = cellHeightDp,
                        columnIndex = columnIndex,
                        totalColumns = columnCount,
                        annotatorSettings = annotatorSettings,
                        markdownComponents = markdownComponents
                    )
                }
            }
        }
    }
}

@Composable
fun UniformTableCell(
    content: String,
    cell: ASTNode,
    style: TextStyle,
    textColor: Color,
    cellWidth: Dp,
    cellHeight: Dp,
    columnIndex: Int,
    totalColumns: Int,
    annotatorSettings: AnnotatorSettings,
    markdownComponents: MarkdownComponents,
) {
    Box(
        modifier = Modifier
            .size(cellWidth, cellHeight) // All cells same size
            .padding(2.dp) // 2dp padding as requested
            .then(
                // Add right border except for last column
                if (columnIndex < totalColumns -1) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        CellContent(
            content = content,
            cell = cell,
            style = style,
            textColor = textColor,
            annotatorSettings = annotatorSettings,
            markdownComponents = markdownComponents
        )
    }
}

@Composable
fun RowScope.EnhancedTableCellAutoWidth(
    content: String,
    cell: ASTNode,
    style: TextStyle,
    textColor: Color,
    isHeader: Boolean,
    columnIndex: Int,
    totalColumns: Int,
    annotatorSettings: AnnotatorSettings,
    markdownComponents: MarkdownComponents,
) {
    Box(
        modifier = Modifier
            .wrapContentWidth() // Cell wraps to content width
            .fillMaxHeight()
            .padding(2.dp) // 2dp padding as requested
            .then(
                // Add right border except for last column
                if (columnIndex < totalColumns - 1) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        // Check if cell contains images or other complex content
        if (cell.children.any { it.type == IMAGE }) {
            // Render complex content (images, etc.)
            MarkdownElement(
                node = cell,
                components = markdownComponents,
                content = content,
                includeSpacer = false
            )
        } else {
            // Render text content
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

@Composable
fun RowScope.EnhancedTableCell(
    content: String,
    cell: ASTNode,
    style: TextStyle,
    textColor: Color,
    isHeader: Boolean,
    columnIndex: Int,
    totalColumns: Int,
    annotatorSettings: AnnotatorSettings,
    markdownComponents: MarkdownComponents,
) {
    Box(
        modifier = Modifier
            .weight(1f) // Equal weight distribution for auto-sizing
            .fillMaxHeight()
            .padding(2.dp) // 2dp padding as requested
            .then(
                // Add right border except for last column
                if (columnIndex < totalColumns - 1) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(0.dp)
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        // Check if cell contains images or other complex content
        if (cell.children.any { it.type == IMAGE }) {
            // Render complex content (images, etc.)
            MarkdownElement(
                node = cell,
                components = markdownComponents,
                content = content,
                includeSpacer = false
            )
        } else {
            // Render text content
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