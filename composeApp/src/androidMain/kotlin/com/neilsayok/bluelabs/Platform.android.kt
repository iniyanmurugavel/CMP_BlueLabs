package com.neilsayok.bluelabs

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()






@Preview
@Composable
fun HomeCard() {

    Card(
        shape = RoundedCornerShape(10.dp)
    ) {

        Column {
//            AsyncImage(
//                model = "https://neilsayok.github.io/imagelib/images/kotlin_dsa_large_img.png",
//                contentDescription = null)

            Image(painter= painterResource(R.drawable.ic_launcher_background), contentDescription = null)


            AssistChip(
                label = { Text("Android") },
                onClick = {},
                shape = RoundedCornerShape(50)
            )


            Text("Kotlin DSA - Algorithms and Data Structures in Kotlin for Android Developers - Google Developers Meetup")

            Row {
                Card(shape = CircleShape, modifier = Modifier.size(24.dp)){
                    AsyncImage(
                        model = "https://avatars.githubusercontent.com/u/21328143?s=400&u=5a97e151d90ba8cc0d67cf73933f004da75dad33&v=4",
                        contentDescription = null)

                }

                Text("Sayok Dey Majumder")
            }

            Text("4 min read")

            TextButton(onClick = {}) {
                Text("Share")
            }
        }

    }


}