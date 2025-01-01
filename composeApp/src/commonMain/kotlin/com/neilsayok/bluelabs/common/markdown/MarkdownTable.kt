package com.neilsayok.bluelabs.common.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.compose.components.CustomMarkdownComponent
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes

const val tableHeaderPattern = """^|\s*-+\s*|$"""


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RenderTable(content: String, node: ASTNode, modifier: Modifier) {
    // Simple table rendering, split content by lines and columns

    val start = node.children[0].startOffset
    val end = node.children[node.children.size - 1].endOffset

    val tableContent = content.subSequence(start, end)

    val rows: List<List<String>> = tableContent.split("\n")
        .map { it.split("|").map(String::trim).filter { cell -> cell.isNotEmpty() } }

    val maxLengthString: String = rows.flatten().maxBy { it.length }

    val hasTitle = tableContent.contains(Regex(tableHeaderPattern))


    val textStyle = TextStyle(fontSize = 14.sp)

    val columnWidth = measureTextWidth(maxLengthString, textStyle)

    var tableRowCount = 0

    Column(modifier = Modifier.wrapContentWidth()) {
        rows.forEachIndexed { rowIndex, row ->
            Row(modifier = Modifier.wrapContentWidth()) {
                row.forEach { cell ->
                    if (hasTitle && rowIndex == 0) {
                        TableCell(
                            text = cell,
                            style = textStyle.merge(TextStyle(fontWeight = FontWeight.Bold)),
                            columnWidth = columnWidth
                        )
                    } else if (hasTitle && rowIndex == 1) {
                        //Do Nothing
                    } else {
                        TableCell(
                            text = cell,
                            style = textStyle,
                            columnWidth = columnWidth,
                            isDark = tableRowCount % 2 == 0
                        )

                    }
                }
            }
            tableRowCount++
        }

    }


}

@Composable
fun measureTextWidth(text: String, style: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val widthInPixels = textMeasurer.measure(text, style).size.width
    return with(LocalDensity.current) { widthInPixels.toDp() }
}


@Composable
fun RowScope.TableCell(
    text: String,
    style: TextStyle = LocalTextStyle.current,
    columnWidth: Dp,
    isDark: Boolean = false
) {
    Row(
        modifier = Modifier
            .width(columnWidth + 32.dp)
            .padding(1.dp)
            .border(1.dp, color = Color.LightGray)
            .background(color = if (isDark) Color.LightGray else Color.White)
    ) {
        Text(
            text = text, style = style, modifier = Modifier.padding(8.dp).fillMaxWidth()
        )
    }

}


val tableRenderer: CustomMarkdownComponent = { elementType, model ->
    if (elementType == GFMElementTypes.TABLE) {
        RenderTable(model.content, model.node, Modifier.wrapContentWidth())
    }
}
