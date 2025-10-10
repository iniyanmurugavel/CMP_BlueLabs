package com.neilsayok.bluelabs.pages.privacy.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.mikepenz.markdown.m3.Markdown
import com.neilsayok.bluelabs.common.ui.markdown.MarkdownHandler
import com.neilsayok.bluelabs.pages.privacy.component.PrivacyPolicyComponent
import com.neilsayok.bluelabs.util.setPageTitle

@Composable
fun PrivacyPolicyScreen(component: PrivacyPolicyComponent) {

    setPageTitle("Privacy Policy")

    Scaffold {

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                MarkdownHandler(PrivacyPolicyText.trimIndent())
            }
        }


    }


}