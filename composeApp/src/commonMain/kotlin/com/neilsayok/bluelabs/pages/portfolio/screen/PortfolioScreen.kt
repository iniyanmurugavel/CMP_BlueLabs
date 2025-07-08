package com.neilsayok.bluelabs.pages.portfolio.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.common.ui.components.LoaderScaffold
import com.neilsayok.bluelabs.data.portfolio.FileType
import com.neilsayok.bluelabs.data.portfolio.FolderType
import com.neilsayok.bluelabs.data.portfolio.projectFileValidator
import com.neilsayok.bluelabs.pages.portfolio.component.PortfolioComponent
import com.neilsayok.bluelabs.pages.portfolio.widgets.AboutMe
import com.neilsayok.bluelabs.pages.portfolio.widgets.ContactMeWidget
import com.neilsayok.bluelabs.pages.portfolio.widgets.ProjectWidget
import com.neilsayok.bluelabs.pages.portfolio.widgets.SectionTitle
import com.neilsayok.bluelabs.pages.portfolio.widgets.SkillWidget
import com.neilsayok.bluelabs.pages.portfolio.widgets.WorkedAtWidget

@Composable
fun PortfolioScreen(component: PortfolioComponent) {

//    val jobsFolderContent by component.jobsFolderContentState.subscribeAsState()
//    val projectsFolderContent by component.projectsFolderContentState.subscribeAsState()
//
//    val fileContents by component.fileContentState.subscribeAsState()
//
//
    val isLoading by component.isLoading.subscribeAsState()
//    val isError by remember { derivedStateOf { jobsFolderContent.isError() || projectsFolderContent.isError() } }

    val fileData by component.fileContents.subscribeAsState()

    LaunchedEffect(Unit) {
        component.getFolderContents()
//        component.getJobsFolderContent()
//        component.getProjectFolderContent()
    }


    LoaderScaffold(
        isLoading = isLoading,
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

            item {
                SectionTitle("Projects")
            }

            item {
                ProjectWidget(
                    fileData.filter { it.projectFileValidator() }.sortedBy { it.order })
            }

            item {
                SectionTitle("Where I've worked")
            }

//            item {
//                WorkedAtWidget(fileContents.values.filter { it.folder == FolderType.Jobs && it.fileType == FileType.MDFile }
//                    .sortedBy { it.order })
//            }

            item {
                SectionTitle("Contact Me")
            }

            item {
                ContactMeWidget()
            }

        }


    }
}