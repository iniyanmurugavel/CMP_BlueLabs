package com.neilsayok.bluelabs.pages.portfolio.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.data.github.getDecodedContent
import com.neilsayok.bluelabs.data.portfolio.FileType
import com.neilsayok.bluelabs.data.portfolio.FileType.MDFile.getFileType
import com.neilsayok.bluelabs.data.portfolio.FolderType
import com.neilsayok.bluelabs.data.portfolio.FolderType.Jobs.getFolderType
import com.neilsayok.bluelabs.data.portfolio.PortfolioFileContents
import com.neilsayok.bluelabs.data.portfolio.getFileNameSubTileOrder
import com.neilsayok.bluelabs.domain.github.GithubRepo
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.pages.portfolio.component.state.PortfolioState
import com.neilsayok.bluelabs.util.BackgroundDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PortfolioComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    val githubRepo: GithubRepo by inject()
    private val coroutineScope: CoroutineScope = CoroutineScope(BackgroundDispatcher)



    var uiState = MutableValue(PortfolioState())


    @OptIn(InternalCoroutinesApi::class)
    fun getFolderContents() {

        coroutineScope.launch {
            githubRepo.getFilesFromFolder(FolderType.Jobs.folderName)
                .onEach { response -> uiState.value.jobs.value = response }.launchIn(this)

            githubRepo.getFilesFromFolder(FolderType.Projects.folderName)
                .onEach { response ->  uiState.value.projects.value = response }.launchIn(this)

            githubRepo.getFilesFromFolder(FolderType.JobsIcon.folderName)
                .onEach { response ->  uiState.value.jobIcons.value = response }.launchIn(this)

            githubRepo.getFilesFromFolder(FolderType.ProjectIcon.folderName)
                .onEach { response ->  uiState.value.projectIcons.value = response }.launchIn(this)

        }

        uiState.value.jobs.subscribe { j ->
            if (j.isSuccess()) {
                val job = j as Response.SuccessResponse<List<GithubResponse>>
                job.data?.forEach { job ->
                    val path = job.path ?: ""
                    coroutineScope.launch {
                        githubRepo.getContent(path).onEach { response ->
                            val currentFileContents = uiState.value.projectsFileContents.value
                            getPortfolioFileContents(path,response, currentFileContents[path])?.let {
                                val updatedFileContents = currentFileContents.toMutableMap()
                                updatedFileContents[path] = it
                                uiState.value.jobsFileContents.value = updatedFileContents
                            }
                        }.launchIn(coroutineScope)
                    }
                }

            }
        }

        uiState.value.projects.subscribe { p ->
            if (p.isSuccess()) {
                val projects = p as Response.SuccessResponse<List<GithubResponse>>
                projects.data?.forEach { project ->
                    val path = project.path ?: ""
                    coroutineScope.launch {
                        githubRepo.getContent(path).onEach { response ->
                            val currentFileContents = uiState.value.projectsFileContents.value
                            getPortfolioFileContents(path,response, currentFileContents[path])?.let {
                                val updatedFileContents = currentFileContents.toMutableMap()
                                updatedFileContents[path] = it
                                uiState.value.projectsFileContents.value = updatedFileContents
                            }
                        }.launchIn(coroutineScope)

                    }
                }

            }
        }
    }


    suspend fun getPortfolioFileContents(
        path: String,
        response: Response<GithubResponse>,
        portfolioFileContents: PortfolioFileContents? = null
    ): PortfolioFileContents? {
        val fileType = portfolioFileContents?.fileType ?: path.getFileType()
        return if (fileType == FileType.MDFile) {
            val folderType = portfolioFileContents?.folder ?:path.getFolderType()
            val (fileName, subtitle, order) = path.getFileNameSubTileOrder()
            val content = if (response.isSuccess()){
                (response as Response.SuccessResponse<GithubResponse>).data?.getDecodedContent()
            } else null


            PortfolioFileContents(
                fileType = fileType,
                folder = folderType,
                fileName = fileName,
                subTitle = subtitle,
                order = order,
                content = content,
                response = response
            )
        } else null
    }





}