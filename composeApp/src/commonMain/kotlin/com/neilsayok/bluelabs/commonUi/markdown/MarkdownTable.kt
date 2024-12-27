package com.neilsayok.bluelabs.commonUi.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.compose.components.CustomMarkdownComponent
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes

const val tableHeaderPattern = "^\\|\\s*-+\\s*\\|$"


@Composable
fun RenderTable(content: String, node: ASTNode, modifier: Modifier) {
    // Simple table rendering, split content by lines and columns

    val start = node.children[0].startOffset
    val end = node.children[node.children.size - 1].endOffset

    val tableContent = content.subSequence(start, end)


    val rows: List<List<String>> = tableContent.split("\n")
        .map { it.split("|").map(String::trim).filter { cell -> cell.isNotEmpty() } }

    val hasTitle = tableContent.contains(Regex(tableHeaderPattern))




    Column(modifier = Modifier.wrapContentWidth().border(1.dp, Color.Green)) {
        rows.forEachIndexed { rowIndex, row ->
            Row(modifier = Modifier.wrapContentWidth().border(1.dp, Color.Red)) {
                row.forEach { cell ->
                    TableCell(text = "cell 1", weight = 1f)

//                    Row(modifier = Modifier.padding(4.dp).border(1.dp, Color.Black)){
//                        Text(
//                            text = cell,
//                            textAlign = TextAlign.Center,
//                            fontSize = if (hasTitle && rowIndex == 0) 16.sp else 14.sp,
//                            fontWeight = if (hasTitle && rowIndex == 0) FontWeight.Bold else FontWeight.Normal,
//                        )
//                    }


                }
            }
        }
    }


}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun TableScreen() {
    // Just a fake data... a Pair of Int and String
    val tableData = (1..100).mapIndexed { index, item ->
        index to "Item $index"
    }
    // Each cell of a column must have the same weight.
    val column1Weight = .3f // 30%
    val column2Weight = .7f // 70%
    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        // Here is the header
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Column 1", weight = column1Weight)
                TableCell(text = "Column 2", weight = column2Weight)
            }
        }
        // Here are all the lines of your table.
        items(tableData) {
            val (id, text) = it
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = id.toString(), weight = column1Weight)
                TableCell(text = text, weight = column2Weight)
            }
        }
    }
}


val tableRenderer: CustomMarkdownComponent = { elementType, model ->
    if (elementType == GFMElementTypes.TABLE) {
        RenderTable(model.content, model.node, Modifier.wrapContentWidth())

    }
}
