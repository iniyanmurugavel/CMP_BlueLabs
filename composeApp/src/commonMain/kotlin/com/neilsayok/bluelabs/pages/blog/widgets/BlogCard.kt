package com.neilsayok.bluelabs.pages.blog.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler


@Composable
fun BlogCard() {
    Card {
        Column {
            AsyncImage(
                model = "https://neilsayok.github.io/imagelib/images/kotlin_dsa_large_img.png",
                contentDescription = "Google Logo",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(128.dp)
            )

            Text("String Interpolation in Kotlin")

            AuthorCard()
            BlogChips()

            MarkdownHandler()

        }
    }


}