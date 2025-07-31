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
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import com.neilsayok.bluelabs.data.github.getDecodedContent
import com.neilsayok.bluelabs.data.portfolio.PortfolioFileContents
import com.neilsayok.bluelabs.domain.util.Response
import kotlinx.coroutines.launch



@Composable
    fun WorkedAtWidget(jobs: List<PortfolioFileContents>) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { jobs.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex.value) {
            jobs.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = currentTab.fileName) }
                )
            }
        }

        Card {
            HorizontalPager(
                state = pagerState,
            ) {
                val response = jobs.getOrNull(pagerState.currentPage)?.response

                Column {
                    if (response?.isSuccess() == true) {
                        (response as Response.SuccessResponse).data?.let {
                            MarkdownHandler(it.getDecodedContent())
                        }
                    }





                }

            }
        }

    }


}