package com.neilsayok.bluelabs.ui.blog.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neilsayok.bluelabs.ui.blog.BlogComponent
import com.neilsayok.bluelabs.ui.home.HomeScreenEvent

@Composable
fun  BlogScreen(component: BlogComponent) {

    Scaffold {

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("Blog Screen")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Blog text = ${component.id}")
            Button(onClick = { component.goBack() }){
                Text("Go To Blog Page")
            }
        }


    }



}