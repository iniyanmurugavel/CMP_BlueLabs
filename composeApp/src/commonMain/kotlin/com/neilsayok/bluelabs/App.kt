package com.neilsayok.bluelabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import bluelabscmp.composeapp.generated.resources.Res
import bluelabscmp.composeapp.generated.resources.compose_multiplatform
import coil3.compose.AsyncImage
import com.neilsayok.bluelabs.ui.commonUi.markdown.MarkdownHandler

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                MarkdownHandler()

        }
    }
}


@Composable
fun TestScreen(){
    var showContent by remember { mutableStateOf(false) }

    AsyncImage(
        model = "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        contentDescription = null,
    )

    Button(onClick = { showContent = !showContent }) {
        Text("Click me!")
    }



    AnimatedVisibility(showContent) {
        val greeting = remember { Greeting().greet() }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(Res.drawable.compose_multiplatform), null)
            Text("Compose: $greeting")
        }
    }
}