package com.neilsayok.bluelabs.pages.home.component

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.neilsayok.bluelabs.data.bloglist.FirebaseResponse
import com.neilsayok.bluelabs.data.documents.BlogFields
import com.neilsayok.bluelabs.domain.firebase.FirebaseRepo
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.util.BackgroundDispatcher
import com.neilsayok.bluelabs.util.layoutType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeComponent(
    componentContext: ComponentContext,
    private val navigateToBlogScreen: (String) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val firebaseRepo: FirebaseRepo by inject()
    private val coroutineScope: CoroutineScope = CoroutineScope(BackgroundDispatcher)


    private val _blogListState = MutableValue<Response<FirebaseResponse<BlogFields>>>(Response.None)
    val blogListState: Value<Response<FirebaseResponse<BlogFields>>> = _blogListState

    init {
        // Observe blog list updates
        coroutineScope.launch {
            firebaseRepo.getAllBlogs()
                .onEach { response ->
                    _blogListState.value = response
                }
                .launchIn(this)
        }
    }

    val containerPadding:Dp
        @Composable get()  = when(layoutType){
        NavigationSuiteType.NavigationBar -> 16.dp
        NavigationSuiteType.NavigationRail,
        NavigationSuiteType.NavigationDrawer -> 64.dp
        else -> 0.dp
    }

    val columnCount:Int
        @Composable get()  = when(layoutType){
        NavigationSuiteType.NavigationBar -> 1
        NavigationSuiteType.NavigationRail,
        NavigationSuiteType.NavigationDrawer -> 4
        else -> 0
    }

    fun onEvent(event: HomeScreenEvent) {

    }
}

sealed interface HomeScreenEvent {
    data object ClickButton : HomeScreenEvent
}