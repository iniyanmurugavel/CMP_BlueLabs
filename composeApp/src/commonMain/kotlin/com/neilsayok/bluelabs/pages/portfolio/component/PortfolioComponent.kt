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
import com.neilsayok.bluelabs.util.BackgroundDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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

    val isLoading = MutableValue<Boolean>(true)
    val fileList: MutableMap<String, Flow<Response<GithubResponse>>> = mutableMapOf()

    private val _fileContents: MutableMap<String, PortfolioFileContents> = mutableMapOf()


    val fileContents: MutableValue<List<PortfolioFileContents>> = MutableValue(emptyList())


    @OptIn(InternalCoroutinesApi::class)
    fun getFolderContents() {


        coroutineScope.launch {
            val jobs = githubRepo.getFilesFromFolder(FolderType.Jobs.folderName)
            val projects = githubRepo.getFilesFromFolder(FolderType.Projects.folderName)
            val jobIcons = githubRepo.getFilesFromFolder(FolderType.JobsIcon.folderName)
            val projectIcons = githubRepo.getFilesFromFolder(FolderType.ProjectIcon.folderName)


            combine(
                jobs, projects, jobIcons, projectIcons
            ) { jobs, projects, jobIcons, projectIcons ->
                listOf(jobs, projects, jobIcons, projectIcons)
            }.onEach { responses ->





                responses.forEach { response ->
                    if (response.isSuccess()) {
                        (response as Response.SuccessResponse<List<GithubResponse>>).data?.onEach {
                            it.path?.let { path ->
                                fileList[path] = githubRepo.getContent(path)
                            }
                        }
                    }
                }

                if (isLoading.value) {
                    getFileContent()
                }

            }.launchIn(this)
        }
    }


    fun getFileContent() {
        coroutineScope.launch {
            fileList.forEach { (path, flow) ->
                val fileType = path.getFileType()
                val folderType = path.getFolderType()
                val (fileName, subtitle, order) = path.getFileNameSubTileOrder()

                _fileContents[path] = PortfolioFileContents(
                    fileType = fileType,
                    folder = folderType,
                    fileName = fileName,
                    subTitle = subtitle,
                    order = order,
                )

                flow.onEach { request ->
                    _fileContents[path]?.response?.value = request
                    if (request is Response.SuccessResponse) {
                        if (fileType == FileType.MDFile) {
                            _fileContents[path]?.content = request.data?.getDecodedContent()
                        }
                    }
                    fileContents.value = _fileContents.values.toSet().toList()
                }.launchIn(this)


            }

        }
    }


}