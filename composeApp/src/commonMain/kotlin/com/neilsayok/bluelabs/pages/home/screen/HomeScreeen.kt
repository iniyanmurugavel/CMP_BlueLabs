package com.neilsayok.bluelabs.pages.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.common.ui.components.LoaderScaffold
import com.neilsayok.bluelabs.pages.home.component.HomeComponent
import com.neilsayok.bluelabs.pages.home.widgets.HomeCard

@Composable
fun HomeScreen(component: HomeComponent) {
    val blogListState by component.blogState.subscribeAsState()
    val state = blogListState

    val snackBarHostState = remember { SnackbarHostState() }




    LoaderScaffold { paddingValues ->

        val columnCount = component.columnCount
        val containerPadding = component.containerPadding

        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = containerPadding),
            columns = GridCells.Fixed(columnCount),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues
        ) {
            items(columnCount){
                Spacer(modifier = Modifier.height(containerPadding))
            }

            items(state) {
                HomeCard(blog = it) { blog ->
                    component.navigateToBlogScreen(blog)
                }
            }
            items(columnCount){
                Spacer(modifier = Modifier.height(containerPadding))
            }

        }


    }
}