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
import com.neilsayok.bluelabs.di.initKoin
import com.neilsayok.bluelabs.ui.commonUi.markdown.MarkdownHandler
import com.neilsayok.bluelabs.util.isAndroid

@Composable
@Preview
fun App() {

    if (!isAndroid())
        initKoin()


    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                MarkdownHandler()

        }
    }
}


