package com.neilsayok.bluelabs.common.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.extendedspans.ExtendedSpans
import com.mikepenz.markdown.compose.extendedspans.RoundedCornerSpanPainter
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.model.markdownExtendedSpans


@Composable
fun MarkdownHandler() {

    val markdown by remember { mutableStateOf(MARKDOWN.trimIndent()) }




    LazyColumn {
        item {
            Markdown(
                content = markdown,
                imageTransformer = Coil3ImageTransformerImpl,
                components = markdownComponents(
                    codeBlock = highlightedCodeBlock,
                    codeFence = highlightedCodeFence,
                    custom = tableRenderer
                ),
                extendedSpans = markdownExtendedSpans {
                    remember {
                        ExtendedSpans(
                            RoundedCornerSpanPainter(),
                        )
                    }
                },
            )
        }

//        item {
//            RichText(
//                state = state,
//            )
//        }

//        item {
//            RichText(
//                modifier = Modifier.padding(16.dp)
//            ) {
//                // requires richtext-commonmark module.
//                val parser = remember(options) { CommonmarkAstNodeParser(options) }
//                val astNode = remember(parser) { parser.parse(markdown)
//                }
//                BasicMarkdown(astNode)
//            }
//        }

    }


}


@Composable
fun CustomTable(
    headers: List<String>,
    rows: List<List<String>>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(Color(0xFFECEFF4))) {
        Row(modifier = Modifier.padding(8.dp)) {
            headers.forEach { header ->
                BasicText(
                    text = header,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f).padding(4.dp)
                )
            }
        }
        rows.forEach { row ->
            Row(modifier = Modifier.padding(8.dp)) {
                row.forEach { cell ->
                    BasicText(
                        text = cell,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        modifier = Modifier.weight(1f).padding(4.dp)
                    )
                }
            }
        }
    }
}


