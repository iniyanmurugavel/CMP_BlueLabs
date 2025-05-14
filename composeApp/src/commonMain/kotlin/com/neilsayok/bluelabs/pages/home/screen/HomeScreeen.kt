package com.neilsayok.bluelabs.pages.home.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.pages.blog.widgets.BlogCard
import com.neilsayok.bluelabs.pages.home.component.HomeComponent


@Composable
fun HomeScreen(component: HomeComponent) {

    val id by component.id.subscribeAsState()

    Scaffold {
        LazyColumn {
            item {
                BlogCard()
            }
        }


//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(5)
//            ){
//                items(15){
//                    HomeCard()
//                }
//            }
//        }


    }

}