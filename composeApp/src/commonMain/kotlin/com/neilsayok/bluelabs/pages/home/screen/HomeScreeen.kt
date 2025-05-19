package com.neilsayok.bluelabs.pages.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.common.ui.components.LoaderScaffold
import com.neilsayok.bluelabs.pages.blog.widgets.BlogCard
import com.neilsayok.bluelabs.pages.home.component.HomeComponent
import com.neilsayok.bluelabs.pages.home.widgets.HomeCard
import com.neilsayok.bluelabs.util.layoutType


@Composable
fun HomeScreen(component: HomeComponent) {
    LoaderScaffold { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.padding(component.containerPadding),
            columns = GridCells.Fixed(component.columnCount),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues){
            items(15){
                HomeCard()
            }
        }




//        LazyColumn {
//            item {
//                BlogCard()
//            }
//        }


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