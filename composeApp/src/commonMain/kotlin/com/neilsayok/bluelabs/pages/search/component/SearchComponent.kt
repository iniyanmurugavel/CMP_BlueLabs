package com.neilsayok.bluelabs.pages.search.component

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.fusekt.Fuse
import com.fusekt.core.FuseOptions
import com.neilsayok.bluelabs.data.bloglist.BlogLoadedFields
import com.neilsayok.bluelabs.data.bloglist.FirebaseResponse
import com.neilsayok.bluelabs.data.bloglist.IndexResult
import com.neilsayok.bluelabs.data.bloglist.IndexResultItem
import com.neilsayok.bluelabs.data.bloglist.getIndexResultObject
import com.neilsayok.bluelabs.data.documents.AuthorFields
import com.neilsayok.bluelabs.data.documents.IndexFields
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.util.layoutType
import kotlinx.serialization.json.Json


class SearchComponent(
    val key: String,
    val index: Value<Response<FirebaseResponse<IndexFields>>>,
    componentContext: ComponentContext,
    val blogState: Value<List<BlogLoadedFields?>>,
    val authorState: Value<Response<FirebaseResponse<AuthorFields>>>,
    private val navigateBlogScreen: (BlogLoadedFields) -> Unit,
) : ComponentContext by componentContext {

    val containerPadding: Dp
        @Composable get() = when (layoutType) {
            NavigationSuiteType.NavigationBar -> 16.dp
            NavigationSuiteType.NavigationRail,
            NavigationSuiteType.NavigationDrawer -> 64.dp

            else -> 0.dp
        }

    val columnCount: Int
        @Composable get() = when (layoutType) {
            NavigationSuiteType.NavigationBar -> 1
            NavigationSuiteType.NavigationRail,
            NavigationSuiteType.NavigationDrawer -> 4

            else -> 0
        }


    fun navigateToBlogScreen(blog: BlogLoadedFields?) {
        blog?.let {
            navigateBlogScreen(blog)
        }
    }


    val options = FuseOptions.withWeightedKeys(
        weightedKeys = mapOf(
            "body" to 0.17,
            "title" to 0.3,
            "author_name" to 0.01,
            "author_uid" to 0.01,
            "url" to 0.01,
            "tags" to 0.5,
        ), includeScore = true, distance = 10
    )

    private val _searchState = MutableValue<List<IndexResultItem>>(emptyList())
    val searchState: Value<List<IndexResultItem>> = _searchState


    fun search(){
        val author = (authorState.value as Response.SuccessResponse).data?.documents
        (index.value as Response.SuccessResponse).data?.documents?.firstOrNull()?.fields?.index?.stringValue?.let {
            val json = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }

            val jsonObject: IndexResult = json.decodeFromString<IndexResult>(it)
            val mapObj = jsonObject.map { jObj -> jObj.getMap() }

            val fuse = Fuse(
                docs = mapObj,
                options = options,
            )
            _searchState.value = fuse.search(key).map { item -> item.item.getIndexResultObject(blogState.value,author) }




        }


    }

}