package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.neilsayok.bluelabs.common.ui.markdown.MARKDOWN
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import com.neilsayok.bluelabs.data.portfolio.PortfolioFileContents
import kotlinx.coroutines.launch


enum class Tabs(val text: String) {
    Explore("TCS"),
    Missions("IBO"),
}

@Composable
    fun WorkedAtWidget(jobs: List<PortfolioFileContents>) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { jobs.size })
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

                    jobs[it].content?.let { markdown -> MarkdownHandler(markdown) }


                }

            }
        }

    }


}