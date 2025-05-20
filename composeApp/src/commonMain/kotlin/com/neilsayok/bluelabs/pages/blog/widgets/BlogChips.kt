package com.neilsayok.bluelabs.pages.blog.widgets

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neilsayok.bluelabs.common.constants.EMPTY_STRING
import com.neilsayok.bluelabs.data.documents.ArrayValue
import com.neilsayok.bluelabs.data.documents.Tags
import com.neilsayok.bluelabs.data.documents.Value
import com.neilsayok.bluelabs.theme.darkTagColors
import com.neilsayok.bluelabs.theme.lightTagColors
import com.russhwolf.settings.Settings
import de.drick.compose.hotpreview.HotPreview

@HotPreview(darkMode = true)
@Composable
fun BlogChipsPreview() {


    BlogChips(listOf("Kotlin","Java","JS","Node"))
}



@Composable
fun BlogChips(tags : List<String?>?) {

    val systemTheme = isSystemInDarkTheme()

    val isDark by remember {
        mutableStateOf(
            if (Settings().keys.contains("isDark")) {
                Settings().getBoolean("isDark", false)
            } else {
                systemTheme
            }
        )
    }

    val colors = if (!isDark) lightTagColors.shuffled() else darkTagColors.shuffled()

    tags?.let {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(horizontal = 16.dp)) {
            tags.forEachIndexed { index, tag->
                AssistChip(
                    onClick = {},
                    label = { Text(tag?: EMPTY_STRING) },
                    shape = RoundedCornerShape(50),
                    leadingIcon = { Icon(Icons.Outlined.Tag,
                        contentDescription = null,
                        tint = colors[index]
                    ) }
                )
            }
        }
    }

}