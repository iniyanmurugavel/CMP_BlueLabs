package com.neilsayok.bluelabs.pages.home.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


@Composable
fun HomeCard() {

    Card {
        Column {
            AsyncImage(
                model = "https://neilsayok.github.io/imagelib/images/kotlin_dsa_large_img.png",
                contentDescription = "Google Logo",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            )

            Column(modifier = Modifier.padding(horizontal = 8.dp)) {

                FilterChip(
                    onClick = {},
                    label = { Text("Kotlin") },
                    selected = true,
                    shape = RoundedCornerShape(50),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color.Red
                    )
                )

                Text(
                    "DSA - Check if an array contains duplicate elements.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Row {
                    Card(modifier = Modifier.size(24.dp), shape = CircleShape) {
                        AsyncImage(
                            model = "https://avatars.githubusercontent.com/u/21328143?s…00&u=5a97e151d90ba8cc0d67cf73933f004da75dad33&v=4",
                            contentDescription = "Google Logo",
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Sayok Dey Majumder")
                }

                Text(
                    "4 mins read", fontWeight = FontWeight.Thin, fontSize = 14.sp
                )
            }

            TextButton(onClick = {}) {
                Text("Share")
            }

        }

    }


}