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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.neilsayok.bluelabs.common.constants.DEFAULT_IMAGE
import com.neilsayok.bluelabs.data.bloglist.BlogLoadedFields
import com.neilsayok.bluelabs.data.documents.BlogFields

@Composable
fun HomeCard(blog: BlogLoadedFields?, navigateToBlogPage : (BlogLoadedFields?)-> Unit) {
    blog?.let {
        Card(modifier = Modifier.height(350.dp).width(200.dp), onClick = {navigateToBlogPage(blog)}) {
            Column {
                AsyncImage(
                    model = blog.bigImg?.stringValue
                        ?: DEFAULT_IMAGE,
                    contentDescription = "Blog Image",
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().weight(3f)
                )

                Column(modifier = Modifier.padding(horizontal = 12.dp).weight(4f)) {

                    blog.genre?.type?.stringValue?.let { tag ->
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

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(modifier = Modifier.size(24.dp), shape = CircleShape) {
                            AsyncImage(
                                model = blog.author?.imgUrl?.stringValue?:DEFAULT_IMAGE,
                                contentDescription = "${blog.author?.name?.stringValue} name",
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(blog.author?.name?.stringValue?:"", maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.labelSmall)
                    }

                    Spacer(modifier = Modifier.size(4.dp))

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