package com.neilsayok.bluelabs.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.ui.home.HomeComponent
import com.neilsayok.bluelabs.ui.home.HomeScreenEvent


@Composable
fun HomeScreen(component: HomeComponent) {

    val id by component.id.subscribeAsState()

    Scaffold {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Home Screen")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Blog text = $id")
            Button(onClick = { component.onEvent(HomeScreenEvent.ClickButton) }) {
                Text("Go To Blog Page")
            }
        }


    }

}