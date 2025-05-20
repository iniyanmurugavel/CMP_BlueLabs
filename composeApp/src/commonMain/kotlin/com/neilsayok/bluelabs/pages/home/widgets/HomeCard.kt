package com.neilsayok.bluelabs.pages.home.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.neilsayok.bluelabs.data.documents.BlogFields

@Composable
fun HomeCard(blog: BlogFields?) {
    blog?.let {
        Card(modifier = Modifier.height(350.dp).width(200.dp)) {
            Column {
                AsyncImage(
                    model = blog.bigImg?.stringValue
                        ?: "https://neilsayok.github.io/imagelib/images/kotlin_dsa_large_img.png",
                    contentDescription = "Blog Image",
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().weight(3f)
                )

                Column(modifier = Modifier.padding(horizontal = 12.dp).weight(4f)) {

                    blog.tags?.arrayValue?.values?.firstOrNull()?.stringValue?.let { tag ->
                        FilterChip(
                            onClick = {},
                            label = { Text(tag) },
                            selected = true,
                            shape = RoundedCornerShape(50),
                        )
                    }

                    Text(
                        text = blog.title?.stringValue
                            ?: "Untitled",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )

                    Row {
                        Card(modifier = Modifier.size(24.dp), shape = CircleShape) {
                            AsyncImage(
                                model = "https://avatars.githubusercontent.com/u/21328143?s=400&u=5a97e151d90ba8cc0d67cf73933f004da75dad33&v=4",
                                contentDescription = "Author Avatar",
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Sayok Dey Majumder", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }

                    blog.readTime?.integerValue.let { readTime ->
                        Text(
                            text = "${readTime ?: "0"} mins read",
                            fontWeight = FontWeight.Thin,
                            fontSize = 14.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }

                TextButton(onClick = {}, modifier = Modifier.padding(start = 8.dp).weight(1f)) {
                    Text("Share")
                }
            }
        }
    }

}