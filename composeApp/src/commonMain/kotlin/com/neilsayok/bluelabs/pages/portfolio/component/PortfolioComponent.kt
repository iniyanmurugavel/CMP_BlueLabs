package com.neilsayok.bluelabs.pages.portfolio.component

import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.data.github.getDecodedContent
import com.neilsayok.bluelabs.data.portfolio.FileType.MDFile.getFileType
import com.neilsayok.bluelabs.data.portfolio.FolderType.Jobs.getFolderType
import com.neilsayok.bluelabs.data.portfolio.PortfolioFileContents
import com.neilsayok.bluelabs.data.portfolio.getFileNameSubTileOrder
import com.neilsayok.bluelabs.domain.github.GithubRepo
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.util.BackgroundDispatcher
import kotlinx.coroutines.CoroutineScope
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


    private val _jobsFolderContentState =
        MutableValue<Response<List<GithubResponse>>>(Response.None)
    val jobsFolderContentState: Value<Response<List<GithubResponse>>> = _jobsFolderContentState


    private val _projectsFolderContentState =
        MutableValue<Response<List<GithubResponse>>>(Response.None)
    val projectsFolderContentState: Value<Response<List<GithubResponse>>> =
        _projectsFolderContentState


    private val _fileContentState =
        MutableValue<MutableMap<String, PortfolioFileContents>>(mutableMapOf())
    val fileContentState: Value<MutableMap<String, PortfolioFileContents>> = _fileContentState


    fun getJobsFolderContent() {
        coroutineScope.launch {
            githubRepo.getFilesFromFolder("jobs").onEach { resp ->
                _jobsFolderContentState.value = resp
                if (resp.isSuccess()) {
                    (resp as Response.SuccessResponse).data?.forEach { file ->
                        file.path?.let { getFileContent(it) }
                    }
                }
            }.launchIn(this)
        }
    }


    fun getProjectFolderContent() {
        coroutineScope.launch {
            githubRepo.getFilesFromFolder("projects").onEach { resp ->
                _projectsFolderContentState.value = resp
                if (resp.isSuccess()) {
                    (resp as Response.SuccessResponse).data?.forEach { file ->
                        file.path?.let { getFileContent(it) }
                    }
                }
            }.launchIn(this)
        }
    }



    fun getFileContent(filePath: String) {
        coroutineScope.launch {
            githubRepo.getContent(filePath).onEach { resp ->
                val folderType = filePath.getFolderType()
                val fileType = filePath.getFileType()
                val (fileName, subtitle, order) = filePath.getFileNameSubTileOrder()
                when (resp) {
                    is Response.SuccessResponse<*> -> {
                        _fileContentState.value[filePath] = PortfolioFileContents(
                            fileType = fileType,
                            folder = folderType,
                            fileName = fileName,
                            icon = null,
                            order = order,
                            subTitle = subtitle,
                            content = (resp as Response.SuccessResponse).data?.getDecodedContent(),
                            response = resp
                        )
                    }

                    else -> {
                        _fileContentState.value[filePath] = PortfolioFileContents(
                            fileType = fileType,
                            folder = folderType,
                            fileName = fileName,
                            response = resp
                        )
                    }
                }
            }.launchIn(this)
        }
    }

}