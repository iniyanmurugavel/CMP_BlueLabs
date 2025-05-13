package com.neilsayok.bluelabs.common.markdown

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.extendedspans.ExtendedSpans
import com.mikepenz.markdown.compose.extendedspans.RoundedCornerSpanPainter
import com.mikepenz.markdown.compose.extendedspans.SquigglyUnderlineSpanPainter
import com.mikepenz.markdown.compose.extendedspans.rememberSquigglyUnderlineAnimator
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.elements.MarkdownCheckBox
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.mikepenz.markdown.model.markdownExtendedSpans
import com.mikepenz.markdown.model.rememberMarkdownState
import com.neilsayok.bluelabs.theme.CODE_BLOCK_BACKGROUND_COLOR
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxThemes


@Composable
fun MarkdownHandler() {
    val highlightsBuilder = Highlights.Builder().theme(SyntaxThemes.atom(darkMode = true))


    var markdown by rememberSaveable(Unit) { mutableStateOf("") }
    LaunchedEffect(Unit) {
        markdown =
            MARKDOWN.trimIndent() + "\n\n" + HTML.trimIndent() + "\n\n" + alltypes.trimIndent()
    }

    SelectionContainer {
        Markdown(
            markdownState = rememberMarkdownState(markdown),
            components = markdownComponents(
                codeBlock = {
                    MarkdownHighlightedCodeBlock(
                        content = it.content, node = it.node, highlights = highlightsBuilder
                    )
                },
                codeFence = {
                    MarkdownHighlightedCodeFence(
                        content = it.content, node = it.node, highlights = highlightsBuilder
                    )
                },
                checkbox = { MarkdownCheckBox(it.content, it.node, it.typography.text) },
                table = { RenderTable(it.content, it.node, Modifier) }
            ),
            imageTransformer = Coil3ImageTransformerImpl,
            extendedSpans = markdownExtendedSpans {
                val animator = rememberSquigglyUnderlineAnimator()
                remember {
                    ExtendedSpans(
                        RoundedCornerSpanPainter(),
                        SquigglyUnderlineSpanPainter(animator = animator)
                    )
                }
            },
            typography = markdownTypography(
                inlineCode = markdownTypography().text.copy(color = Color.White)
            ),
            colors = markdownColor(
                codeBackground = CODE_BLOCK_BACKGROUND_COLOR,
                inlineCodeBackground = CODE_BLOCK_BACKGROUND_COLOR
            ),
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}



