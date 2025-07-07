package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun SkillWidget() {

    val skillList = mapOf(
        "Android" to 5,
        "Kotlin" to 5,
        "Java" to 4,
        "Jetpack Compose" to 5,
    )

    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        skillList.forEach { (skill, level) ->
            FilterChip(
                selected = true,
                onClick = {},
                label = { Text(skill) },
                trailingIcon = {
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Skill Level"
                        )
                        Text("$level")
                    }
                }


            )
        }
    }


}