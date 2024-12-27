package com.neilsayok.bluelabs.pages.indexer.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.neilsayok.bluelabs.pages.indexer.component.IndexerComponent

@Composable
fun IndexerScreen(component: IndexerComponent) {

    Scaffold {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Indexer Screen")
        }


    }


}