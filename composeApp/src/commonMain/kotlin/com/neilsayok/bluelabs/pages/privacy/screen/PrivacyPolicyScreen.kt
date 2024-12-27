package com.neilsayok.bluelabs.pages.privacy.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.neilsayok.bluelabs.pages.privacy.component.PrivacyPolicyComponent

@Composable
fun PrivacyPolicyScreen(component: PrivacyPolicyComponent) {

    Scaffold {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Privacy Policy Screen")
        }


    }


}