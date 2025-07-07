package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.neilsayok.bluelabs.common.ui.components.LoaderBox
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.data.github.getDecodedContent
import com.neilsayok.bluelabs.data.portfolio.PortfolioFileContents
import com.neilsayok.bluelabs.domain.util.Response


@Composable
fun ProjectWidget(projectsFileContent: List<PortfolioFileContents>) {
    LoaderBox(
        isLoading = projectsFileContent.any { !it.response.isSuccess() }
    ) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            projectsFileContent.forEach{ fileContent->
                ProjectCard(fileContent)
            }

        }
    }

}

@Composable
fun ProjectCard(fileContents: PortfolioFileContents) {

    var isExpanded by remember { mutableStateOf(false) }
    val transitionState = remember {
        MutableTransitionState(isExpanded).apply {
            targetState = isExpanded
        }
    }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300)
    )




    Card(modifier = Modifier.widthIn(min = 100.dp)) {
        LoaderBox(
            isLoading = fileContents.response.isLoading(),
            isError = fileContents.response.isError(),
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, end = 6.dp, top = 24.dp, bottom = 24.dp)
                )
                {
                    Box(
                        modifier = Modifier.border(
                            BorderStroke(1.dp, MaterialTheme.colorScheme.outline), shape = CircleShape
                        ).size(32.dp), contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Cloud, null, modifier = Modifier.size(24.dp))
                    }
                    Column {
                        Text(
                            text = fileContents.fileName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        fileContents.subTitle?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Thin
                            )
                        }

                    }


                    IconButton(onClick = {
                        isExpanded = !isExpanded
                        transitionState.targetState = isExpanded
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            null,
                            modifier = Modifier.rotate(rotationAngle)
                        )
                    }
                }

                AnimatedVisibility(isExpanded) {
                    Column {
                        fileContents.content?.let { MarkdownHandler(it) }

                    }
                }
            }
        }

    }
}