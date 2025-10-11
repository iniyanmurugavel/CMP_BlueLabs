package com.neilsayok.bluelabs.pages.privacy.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import com.neilsayok.bluelabs.pages.privacy.component.PrivacyPolicyComponent
import com.neilsayok.bluelabs.util.setPageTitle

@Composable
fun PrivacyPolicyScreen(component: PrivacyPolicyComponent) {

    setPageTitle("Privacy Policy")

    Scaffold { paddingValues ->
        Card(shape = RectangleShape, modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    MarkdownHandler(PrivacyPolicyText.trimIndent())
                }
                item {
                    Text(
                        "All the data on the website are provided to the best of the author's knowledge, and the author is not legally bound if the data is incorrect.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                item {
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }


    }


}