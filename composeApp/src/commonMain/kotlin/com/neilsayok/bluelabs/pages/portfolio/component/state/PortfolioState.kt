package com.neilsayok.bluelabs.pages.portfolio.component.state

import com.arkivanov.decompose.value.MutableValue
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.data.portfolio.PortfolioFileContents
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.pages.portfolio.component.PortfolioComponent
import kotlin.let

data class PortfolioState(
    val jobs:MutableValue<Response<List<GithubResponse>>> = MutableValue(Response.None),
    val projects:MutableValue<Response<List<GithubResponse>>> = MutableValue(Response.None),
    val jobIcons:MutableValue<Response<List<GithubResponse>>> = MutableValue(Response.None),
    val projectIcons:MutableValue<Response<List<GithubResponse>>> = MutableValue(Response.None),

    val projectsFileContents: MutableValue<MutableMap<String, PortfolioFileContents>> = MutableValue(mutableMapOf<String, PortfolioFileContents>()),
    val jobsFileContents: MutableValue<MutableMap<String, PortfolioFileContents>> = MutableValue(mutableMapOf<String, PortfolioFileContents>()),

    ){
    fun getProjectsIcon(contents: PortfolioFileContents) : String?{
        return if (projectIcons.value.isSuccess()){
            val path = (projectIcons.value as Response.SuccessResponse).data?.firstOrNull{it.path?.contains(contents.getIconName()) == true}?.path
            if (path.isNullOrBlank()) null else "https://neilsayok.github.io/imagelib/$path"
        }else{
            null
        }
    }

    fun getJobsIcon(contents: PortfolioFileContents) : String?{
        return if (jobIcons.value.isSuccess()){
            val path = (jobIcons.value as Response.SuccessResponse).data?.firstOrNull{it.path?.contains(contents.getIconName()) == true}?.path
            if (path.isNullOrBlank()) null else "https://neilsayok.github.io/imagelib/$path"
        }else{
            null
        }
    }

}