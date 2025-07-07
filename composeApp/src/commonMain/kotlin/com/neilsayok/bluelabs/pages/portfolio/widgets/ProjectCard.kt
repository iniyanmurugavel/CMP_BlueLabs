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
import com.neilsayok.bluelabs.common.ui.markdown.MARKDOWN
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import de.drick.compose.hotpreview.HotPreview

@HotPreview(widthDp = 411, heightDp = 891, density = 2.625f)
@Composable
fun ProjectCardPreview() {
    ProjectCard()
}


@Composable
fun ProjectCard() {

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
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp, end = 6.dp, top = 24.dp, bottom = 24.dp)
        ) {
            Box(
                modifier = Modifier.border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.outline), shape = CircleShape
                ).size(32.dp), contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Cloud, null, modifier = Modifier.size(24.dp))
            }
            Text(
                "Weather App",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

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
                MarkdownHandler(MARKDOWN)

            }
        }
    }
}