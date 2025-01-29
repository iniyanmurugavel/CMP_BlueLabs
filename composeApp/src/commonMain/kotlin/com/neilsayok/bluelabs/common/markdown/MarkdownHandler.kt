package com.neilsayok.bluelabs.common.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.model.markdownExtendedSpans
import com.neilsayok.bluelabs.theme.CODE_BLOCK_BACKGROUND_COLOR


@Composable
fun MarkdownHandler() {

    val markdown by remember { mutableStateOf(MARKDOWN.trimIndent()) }


    Column(modifier = Modifier.fillMaxWidth()) {

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
            colors = markdownColor(
                inlineCodeBackground = CODE_BLOCK_BACKGROUND_COLOR,
                inlineCodeText = Color.White,
            )
        )


    }


}



