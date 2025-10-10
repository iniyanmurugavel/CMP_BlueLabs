package com.neilsayok.bluelabs.pages.pagenotfound.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.neilsayok.bluelabs.pages.editor.component.PageNotFoundComponent
import com.neilsayok.bluelabs.util.setPageTitle

@Composable
fun PageNotFoundScreen(component: PageNotFoundComponent) {
    setPageTitle("404 - Page Not Found")
    Scaffold {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("404 - Page Not Found Screen")
        }


    }


}