package com.neilsayok.bluelabs.pages.portfolio.component.state

import com.arkivanov.decompose.value.MutableValue
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.data.portfolio.PortfolioFileContents
import com.neilsayok.bluelabs.domain.util.Response

data class PortfolioState(
    val jobs:MutableValue<Response<List<GithubResponse>>> = MutableValue<Response<List<GithubResponse>>>(Response.None),
    val projects:MutableValue<Response<List<GithubResponse>>> = MutableValue<Response<List<GithubResponse>>>(Response.None),
    val jobIcons:MutableValue<Response<List<GithubResponse>>> = MutableValue<Response<List<GithubResponse>>>(Response.None),
    val projectIcons:MutableValue<Response<List<GithubResponse>>> = MutableValue<Response<List<GithubResponse>>>(Response.None),

    val projectsFileContents: MutableValue<MutableMap<String, PortfolioFileContents>> = MutableValue(mutableMapOf<String, PortfolioFileContents>()),
    val jobsFileContents: MutableValue<MutableMap<String, PortfolioFileContents>> = MutableValue(mutableMapOf<String, PortfolioFileContents>()),

    )