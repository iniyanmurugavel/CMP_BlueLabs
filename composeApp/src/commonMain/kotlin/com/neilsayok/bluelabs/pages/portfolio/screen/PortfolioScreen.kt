package com.neilsayok.bluelabs.pages.portfolio.screen

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neilsayok.bluelabs.pages.portfolio.component.PortfolioComponent
import com.neilsayok.bluelabs.pages.portfolio.widgets.AboutMe
import com.neilsayok.bluelabs.pages.portfolio.widgets.ContactMeWidget
import com.neilsayok.bluelabs.pages.portfolio.widgets.ProjectCard
import com.neilsayok.bluelabs.pages.portfolio.widgets.SectionTitle
import com.neilsayok.bluelabs.pages.portfolio.widgets.SkillWidget
import com.neilsayok.bluelabs.pages.portfolio.widgets.WorkedAtWidget

@Composable
fun PortfolioScreen(component: PortfolioComponent) {



    Scaffold {

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Text("Hi I am Sayok", style = MaterialTheme.typography.headlineLarge)
            }

            item {
                SectionTitle("About Me")
            }

            item{
                AboutMe()
            }

            item {
                SectionTitle("Skills")
            }

            item {
                SkillWidget()
            }

            item {
                SectionTitle("Projects")
            }

            item {
                FlowRow {
                    for(i in 0..10) {
                        ProjectCard()
                    }
                }
            }

            item {
                SectionTitle("Where I've worked")
            }

            item {
                WorkedAtWidget()
            }

            item {
                SectionTitle("Contact Me")
            }

            item {
                ContactMeWidget()
            }

        }


    }
}