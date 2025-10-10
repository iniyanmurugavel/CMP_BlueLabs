package com.neilsayok.bluelabs.pages.search.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.common.ui.components.LoaderScaffold
import com.neilsayok.bluelabs.data.bloglist.IndexResultItem
import com.neilsayok.bluelabs.pages.home.widgets.HomeCard
import com.neilsayok.bluelabs.pages.search.component.SearchComponent
import com.neilsayok.bluelabs.util.setPageTitle

@Composable
fun SearchScreen(component: SearchComponent) {


    val searchResult by component.searchState.subscribeAsState()

    LaunchedEffect(Unit) {
        component.search()
        setPageTitle("Search : ${component.key}")
    }

    LoaderScaffold { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.padding(component.containerPadding),
            columns = GridCells.Fixed(component.columnCount),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = paddingValues
        ) {

            items(searchResult) {
                HomeCard(blog = it.blog) { blog ->
                    component.navigateToBlogScreen(blog)
                }
            }

        }


    }


}