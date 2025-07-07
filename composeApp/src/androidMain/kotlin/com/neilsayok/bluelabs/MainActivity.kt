package com.neilsayok.bluelabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.retainedComponent
import com.neilsayok.bluelabs.navigation.RootComponent
import com.neilsayok.bluelabs.pages.portfolio.widgets.ProjectCard
import org.jetbrains.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = retainedComponent { RootComponent(it) }

        setContent {
            App(root)
        }
    }
}


@Preview
@Composable
fun ProjectCardPreview() {
    ProjectCard(it)
}

