package com.neilsayok.bluelabs.pages.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.common.ui.components.LoaderScaffold
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.pages.home.component.HomeComponent
import com.neilsayok.bluelabs.pages.home.widgets.HomeCard

@Composable
fun HomeScreen(component: HomeComponent) {
    val blogListState by component.blogListState.subscribeAsState()
    val state = blogListState
    println("blogListState : $blogListState")
    LoaderScaffold(
        isLoading = state == Response.Loading,
        isError = state is Response.ExceptionResponse
    ) { paddingValues ->
        if (state is Response.SuccessResponse) {
            LazyVerticalGrid(
                modifier = Modifier.padding(component.containerPadding),
                columns = GridCells.Fixed(component.columnCount),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = paddingValues
            ) {
                items(state.data?.documents?.size ?: 0) { index ->
                    val blog = state.data?.documents?.get(index)
                    blog?.let { HomeCard(blog = it.fields) }
                } }
        }


    }
}