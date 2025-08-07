package com.neilsayok.bluelabs.pages.indexer.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.neilsayok.bluelabs.data.bloglist.FirebaseResponse
import com.neilsayok.bluelabs.data.documents.AuthorFields
import com.neilsayok.bluelabs.data.documents.BlogFields
import com.neilsayok.bluelabs.data.github.getDecodedContent
import com.neilsayok.bluelabs.domain.firebase.FirebaseRepo
import com.neilsayok.bluelabs.domain.github.GithubRepo
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.pages.indexer.component.state.IndexerState
import com.neilsayok.bluelabs.pages.indexer.data.IndexItem
import com.neilsayok.bluelabs.BuildKonfig
import com.neilsayok.bluelabs.util.BackgroundDispatcher
import com.neilsayok.bluelabs.util.Log
import com.neilsayok.bluelabs.util.convertToPlainText
import com.neilsayok.bluelabs.util.sha256Hash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IndexerComponent(
    componentContext: ComponentContext,
    blogResponse: Value<Response<FirebaseResponse<BlogFields>>>,
    authorResponse: Value<Response<FirebaseResponse<AuthorFields>>>,
) : ComponentContext by componentContext, KoinComponent {

    private val firebaseRepo: FirebaseRepo by inject()
    private val githubRepo: GithubRepo by inject()
    private val coroutineScope: CoroutineScope = CoroutineScope(BackgroundDispatcher)

    private val _uiState = MutableValue(IndexerState())
    val uiState: Value<IndexerState> = _uiState

    init {
        // Initialize with passed data instead of making new API calls
        _uiState.value = _uiState.value.copy(
            blogResponse = blogResponse.value,
            authorResponse = authorResponse.value,
            isLoading = false
        )
        
        // Subscribe to changes in the passed data
        blogResponse.subscribe { response ->
            _uiState.value = _uiState.value.copy(blogResponse = response)
        }
        
        authorResponse.subscribe { response ->
            _uiState.value = _uiState.value.copy(authorResponse = response)
        }
    }

    suspend fun convertToJson(): List<IndexItem> {
        val state = _uiState.value
        val blogs = (state.blogResponse as? Response.SuccessResponse)?.data?.documents ?: return emptyList()
        val authors = (state.authorResponse as? Response.SuccessResponse)?.data?.documents ?: return emptyList()
        
        val authorMap = authors.filterNotNull().associate { doc ->
            doc.name?.substringAfterLast("/") to doc.fields
        }

        val publishedBlogs = blogs.filterNotNull().filter { 
            it.fields?.isPublished?.booleanValue == true 
        }

        return publishedBlogs.mapNotNull { blog ->
            blog.fields?.let { blogFields ->
                val authorId = blogFields.author?.referenceValue?.substringAfterLast("/")
                val author = authorMap[authorId]
                
                if (author != null) {
                    // Fetch GitHub content
                    val readmeContent = blogFields.readmeFile?.stringValue?.let { readmePath ->
                        fetchGitHubContent(readmePath)
                    } ?: ""

                    IndexItem(
                        body = convertToPlainText(readmeContent),
                        tags = blogFields.tags?.arrayValue?.values?.mapNotNull { 
                            it?.stringValue 
                        } ?: emptyList(),
                        title = convertToPlainText(blogFields.title?.stringValue ?: ""),
                        authorUid = author.uid?.stringValue ?: "",
                        authorName = author.name?.stringValue ?: "",
                        url = "${getCurrentBaseUrl()}/${author.uid?.stringValue}/${blogFields.urlStr?.stringValue}"
                    )
                } else null
            }
        }
    }

    private suspend fun fetchGitHubContent(path: String): String {
        return try {
            var content = ""
            githubRepo.getContent(path).collect { response ->
                if (response is Response.SuccessResponse) {
                    content = response.data?.getDecodedContent() ?: ""
                }
            }
            content
        } catch (e: Exception) {
            ""
        }
    }

    private fun getCurrentBaseUrl(): String {
        // This should be configured based on your actual domain
        return "https://neilsayok.github.io/BlueLabsCMP"
    }

    suspend fun handleSubmit(password: String, onResult: (Boolean, String) -> Unit) {
        try {
            val indexItems = convertToJson()
            val jsonString = Json.encodeToString(indexItems)
            
            val hashedPassword = sha256Hash(password, BuildKonfig.SHA_SECRET_KEY)
            Log.d("hashedPassword", hashedPassword)
            // Get the admin password from authors (assuming CzflH2N4F0Cp6PrCkaji is the admin ID)
            val state = _uiState.value
            val authors = (state.authorResponse as? Response.SuccessResponse)?.data?.documents
            val adminAuthor = authors?.firstOrNull { 
                it?.name?.contains("CzflH2N4F0Cp6PrCkaji") == true
            }
            
            val adminPassword = adminAuthor?.fields?.pass?.stringValue
            
            if (hashedPassword == adminPassword) {
                // Update Firebase index
                firebaseRepo.updateIndex(jsonString).collect { response ->
                    when (response) {
                        is Response.SuccessResponse -> {
                            onResult(true, "Index updated successfully!")
                        }
                        is Response.ExceptionResponse -> {
                            onResult(false, "Failed to update index: ${response.message}")
                        }
                        else -> {
                            onResult(false, "Updating index...")
                        }
                    }
                }
            } else {
                onResult(false, "Invalid password!")
            }
        } catch (e: Exception) {
            onResult(false, "Error: ${e.message}")
        }
    }
}