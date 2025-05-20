package com.neilsayok.bluelabs.pages.blog.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.neilsayok.bluelabs.common.constants.DEFAULT_IMAGE
import com.neilsayok.bluelabs.common.constants.EMPTY_STRING
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import com.neilsayok.bluelabs.data.bloglist.BlogLoadedFields


@Composable
fun BlogCard(blog: BlogLoadedFields) {
    Card {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(
                model = blog.bigImg?.stringValue ?: DEFAULT_IMAGE,
                contentDescription = "${blog.title} image",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(300.dp)
            )

            Text(
                text = blog.title?.stringValue ?: EMPTY_STRING,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            AuthorCard(blog.author, blog.posted?.timestampValue)

            val chips: List<String?>? = blog.tags?.arrayValue?.values?.map { it?.stringValue }
            BlogChips(chips)

            MarkdownHandler()

        }
    }


}