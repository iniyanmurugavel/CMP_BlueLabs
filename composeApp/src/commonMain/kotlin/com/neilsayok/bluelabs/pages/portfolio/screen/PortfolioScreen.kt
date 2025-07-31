package com.neilsayok.bluelabs.pages.portfolio.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.common.ui.components.LoaderScaffold
import com.neilsayok.bluelabs.pages.portfolio.component.PortfolioComponent
import com.neilsayok.bluelabs.pages.portfolio.widgets.AboutMe
import com.neilsayok.bluelabs.pages.portfolio.widgets.ContactMeWidget
import com.neilsayok.bluelabs.pages.portfolio.widgets.ProjectWidget
import com.neilsayok.bluelabs.pages.portfolio.widgets.SectionTitle
import com.neilsayok.bluelabs.pages.portfolio.widgets.SkillWidget
import com.neilsayok.bluelabs.pages.portfolio.widgets.WorkedAtWidget

@Composable
fun PortfolioScreen(component: PortfolioComponent) {

    val uiState by component.uiState.subscribeAsState()

    val projects = uiState.projectsFileContents.subscribeAsState()
    val jobs = uiState.jobsFileContents.subscribeAsState()

    LaunchedEffect(Unit) {
        component.getFolderContents()
    }


    LoaderScaffold(
        calledApis = listOf(uiState.jobs, uiState.projects)
    ) { paddingValues ->

        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            item {
                Text("Hi I am Sayok", style = MaterialTheme.typography.headlineLarge)
            }

            item {
                SectionTitle("About Me")
            }

            item {
                AboutMe()
            }

            item {
                SectionTitle("Skills")
            }

            item {
                SkillWidget()
            }

            if(projects.value.isNotEmpty()) {
                item {
                    SectionTitle("Projects")
                }
                item {
                    ProjectWidget(projects.value.values)
                }
            }




            if (jobs.value.isNotEmpty()) {
                item {
                    SectionTitle("Where I've worked")
                }

                item {
                    WorkedAtWidget(jobs.value.values.toList())
                }
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