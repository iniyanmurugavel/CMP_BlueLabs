package com.neilsayok.bluelabs.pages.portfolio.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.neilsayok.bluelabs.data.github.GithubResponse
import com.neilsayok.bluelabs.domain.github.GithubRepo
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.util.BackgroundDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class PortfolioComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent{

    val githubRepo: GithubRepo by inject()
    private val coroutineScope: CoroutineScope = CoroutineScope(BackgroundDispatcher)


    private val _readmeContentState =
        MutableValue<Response<GithubResponse>>(Response.None)
    val readmeContentState: Value<Response<GithubResponse>> = _readmeContentState

}