package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.neilsayok.bluelabs.common.ui.markdown.MARKDOWN
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import de.drick.compose.hotpreview.HotPreview
import kotlinx.coroutines.launch


enum class Tabs(val text: String) {
    Explore("TCS"),
    Missions("IBO"),
}

@Composable
fun WorkedAtWidget() {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex.value) {
            Tabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = { Text(text = currentTab.text) }
                )
            }
        }

        Card {
            HorizontalPager(
                state = pagerState,
            ) {
                Column {

                    MarkdownHandler(MARKDOWN)


                }

            }
        }

    }


}