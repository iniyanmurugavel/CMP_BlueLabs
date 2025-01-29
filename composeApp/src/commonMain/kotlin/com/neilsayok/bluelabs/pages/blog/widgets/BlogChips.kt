package com.neilsayok.bluelabs.pages.blog.widgets

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BlogChips() {
    FlowRow {
        for (i in 1..4) {
            AssistChip(
                onClick = {},
                label = { Text("Kotlin") },
                shape = RoundedCornerShape(50),
                leadingIcon = { Icon(Icons.Outlined.Tag, contentDescription = null) }
            )
        }
    }
}