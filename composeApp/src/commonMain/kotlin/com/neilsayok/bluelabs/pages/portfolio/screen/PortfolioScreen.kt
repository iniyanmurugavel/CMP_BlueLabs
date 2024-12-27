package com.neilsayok.bluelabs.pages.portfolio.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.neilsayok.bluelabs.pages.portfolio.component.PortfolioComponent

@Composable
fun PortfolioScreen(component: PortfolioComponent) {

    Scaffold {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Portfolio Screen")
        }


    }


}