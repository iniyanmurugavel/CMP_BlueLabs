package com.neilsayok.bluelabs.pages.editor.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.neilsayok.bluelabs.pages.editor.component.EditorComponent

@Composable
fun EditorScreen(component: EditorComponent) {

    Scaffold {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Editor Screen")
        }


    }


}