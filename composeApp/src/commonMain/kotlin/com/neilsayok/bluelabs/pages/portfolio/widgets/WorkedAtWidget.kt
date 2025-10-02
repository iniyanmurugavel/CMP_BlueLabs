package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.data.github.getDecodedContent
import com.neilsayok.bluelabs.data.portfolio.PortfolioFileContents
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.util.Log
import com.neilsayok.bluelabs.util.loadImage
import io.ktor.util.collections.getValue
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.launch


@OptIn(InternalAPI::class)
@Composable
fun WorkedAtWidget(
    jobs: List<PortfolioFileContents>, getJobsIcon: (PortfolioFileContents) -> String?
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { jobs.size })

    Log.d("currentPage", pagerState.currentPage.toString())


    Column {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            jobs.forEachIndexed { index, currentTab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)

                        }
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            getJobsIcon(currentTab)?.let {
                                Image(
                                    painter = loadImage(it),
                                    contentDescription = null,
                                    modifier = Modifier.height(16.dp),
                                    contentScale = ContentScale.FillHeight
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                            }

                            Text(text = currentTab.fileName)
                        }

                    },

                    )
            }
        }

        Card(shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp)) {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                val currentJobContent = jobs.firstOrNull { it.order == (page + 1) }?.content
                Column {
                    currentJobContent?.let { markdown -> MarkdownHandler(markdown) }
                }

            }
        }

    }


}