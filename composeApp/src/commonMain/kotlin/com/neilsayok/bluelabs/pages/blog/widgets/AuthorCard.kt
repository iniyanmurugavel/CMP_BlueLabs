package com.neilsayok.bluelabs.pages.blog.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Composable
fun AuthorCard() {
    Row {
        Card(modifier = Modifier.size(48.dp), shape = CircleShape) {
            AsyncImage(
                model = "https://avatars.githubusercontent.com/u/21328143?s…00&u=5a97e151d90ba8cc0d67cf73933f004da75dad33&v=4",
                contentDescription = "Google Logo",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            )
        }

        Column {
            Text("Sayok Dey Majumder".uppercase(), style = MaterialTheme.typography.headlineSmall)
            Text("Posted On: 28th September 2024", fontWeight = FontWeight.Thin)
        }
    }
}