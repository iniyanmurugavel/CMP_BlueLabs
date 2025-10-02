package com.neilsayok.bluelabs.pages.editor.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.neilsayok.bluelabs.BuildKonfig
import com.neilsayok.bluelabs.data.bloglist.FirebaseResponse
import com.neilsayok.bluelabs.data.documents.AuthorFields
import com.neilsayok.bluelabs.data.documents.GenreFields
import com.neilsayok.bluelabs.data.github.getDecodedContent
import com.neilsayok.bluelabs.domain.firebase.FirebaseRepo
import com.neilsayok.bluelabs.domain.github.GithubRepo
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.pages.editor.component.state.BlogFormData
import com.neilsayok.bluelabs.pages.editor.component.state.EditorState
import com.neilsayok.bluelabs.pages.editor.component.state.ValidationError
import com.neilsayok.bluelabs.util.BackgroundDispatcher
import com.neilsayok.bluelabs.util.Log
import com.neilsayok.bluelabs.util.sha256Hash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

@OptIn(ExperimentalTime::class)
class EditorComponent(
    componentContext: ComponentContext,
    genreResponse: Value<Response<FirebaseResponse<GenreFields>>>,
    authorResponse: Value<Response<FirebaseResponse<AuthorFields>>>,
) : ComponentContext by componentContext, KoinComponent {

    private val firebaseRepo: FirebaseRepo by inject()
    private val githubRepo: GithubRepo by inject()
    private val coroutineScope: CoroutineScope = CoroutineScope(BackgroundDispatcher)

    private val _uiState = MutableValue(EditorState())
    val uiState: Value<EditorState> = _uiState

    init {
        // Initialize with passed data instead of making new API calls
        _uiState.value = _uiState.value.copy(
            genreResponse = genreResponse.value,
            authorResponse = authorResponse.value,
            isLoading = false
        )
        
        // Subscribe to changes in the passed data
        genreResponse.subscribe { response ->
            _uiState.value = _uiState.value.copy(genreResponse = response)
        }
        
        authorResponse.subscribe { response ->
            _uiState.value = _uiState.value.copy(authorResponse = response)
        }
    }

    fun updateFormData(formData: BlogFormData) {
        _uiState.value = _uiState.value.copy(
            formData = formData,
            validationErrors = validateForm(formData)
        )
    }

    fun addTag(tag: String) {
        val currentTags = _uiState.value.formData.tags
        if (currentTags.size < 4 && tag.isNotBlank() && !currentTags.contains(tag)) {
            val newFormData = _uiState.value.formData.copy(
                tags = currentTags + tag
            )
            updateFormData(newFormData)
        }
    }

    fun removeTag(index: Int) {
        val currentTags = _uiState.value.formData.tags
        if (index in currentTags.indices) {
            val newFormData = _uiState.value.formData.copy(
                tags = currentTags.filterIndexed { i, _ -> i != index }
            )
            updateFormData(newFormData)
        }
    }

    fun loadMarkdownFromFile(fileName: String) {
        if (fileName.endsWith(".md")) {
            coroutineScope.launch {
                try {
                    githubRepo.getContent(fileName).firstOrNull()?.let { response ->
                        if (response is Response.SuccessResponse) {
                            val content = response.data?.getDecodedContent() ?: ""
                            val newFormData = _uiState.value.formData.copy(
                                mdFileName = fileName,
                                markdownContent = content
                            )
                            updateFormData(newFormData)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("EditorComponent", "Failed to load markdown: ${e.message}")
                }
            }
        }
    }

    private fun validateForm(formData: BlogFormData): Set<ValidationError> {
        val errors = mutableSetOf<ValidationError>()
        
        if (formData.selectedGenre.isBlank()) errors.add(ValidationError.GENRE_EMPTY)
        if (formData.tinyImageUrl.isBlank()) errors.add(ValidationError.TINY_IMAGE_URL_EMPTY)
        if (formData.bigImageUrl.isBlank()) errors.add(ValidationError.BIG_IMAGE_URL_EMPTY)
        if (formData.title.isBlank()) errors.add(ValidationError.TITLE_EMPTY)
        if (formData.title.length > 2000) errors.add(ValidationError.TITLE_TOO_LONG)
        if (formData.tags.isEmpty()) errors.add(ValidationError.TAGS_EMPTY)
        if (formData.tags.size > 4) errors.add(ValidationError.TAGS_TOO_MANY)
        if (formData.mdFileName.isBlank()) errors.add(ValidationError.MD_FILE_EMPTY)
        if (formData.markdownContent.isBlank()) errors.add(ValidationError.MARKDOWN_CONTENT_EMPTY)
        if (formData.selectedAuthor.isBlank()) errors.add(ValidationError.AUTHOR_EMPTY)
        
        return errors
    }

    private fun generateUrlTitle(title: String): String {
        if (title.isEmpty()) return ""
        val randomSuffix = Random.nextInt(100000, 999999).toString()
        return title
            .lowercase()
            .replace(Regex("[^a-zA-Z0-9]+"), "-")
            .replace(Regex("^-+|-+$"), "") + 
            "-" + randomSuffix
    }

    fun calculateReadTime(content: String): Int {
        val words = content.split(Regex("\\s+")).size
        return maxOf(1, words / 200) // Assuming 200 words per minute reading speed
    }

    suspend fun submitBlog(password: String, onResult: (Boolean, String) -> Unit) {
        val currentState = _uiState.value
        val formData = currentState.formData
        
        // Validate form
        val validationErrors = validateForm(formData)
        if (validationErrors.isNotEmpty()) {
            onResult(false, "Please fill all required fields correctly!")
            return
        }
        
        _uiState.value = currentState.copy(isSubmitting = true)
        
        try {
            // Hash password
            val hashedPassword = sha256Hash(password, BuildKonfig.SHA_SECRET_KEY)
            
            // Get author data to verify password
            val authors = (currentState.authorResponse as? Response.SuccessResponse)?.data?.documents
            val selectedAuthor = authors?.firstOrNull { 
                it?.name?.substringAfterLast("/") == formData.selectedAuthor 
            }
            
            val adminPassword = selectedAuthor?.fields?.pass?.stringValue
            
            if (hashedPassword != adminPassword) {
                onResult(false, "Invalid password!")
                _uiState.value = currentState.copy(isSubmitting = false)
                return
            }
            
            // Create blog data
            val urlTitle = generateUrlTitle(formData.title)
            
            val blogData = mapOf(
                "fields" to mapOf(
                    "isPublished" to mapOf("booleanValue" to formData.isPublished),
                    "author" to mapOf("referenceValue" to "projects/bluelabs-41aef/databases/(default)/documents/author/${formData.selectedAuthor}"),
                    "big_img" to mapOf("stringValue" to formData.bigImageUrl),
                    "genre" to mapOf("referenceValue" to "projects/bluelabs-41aef/databases/(default)/documents/genre/${formData.selectedGenre}"),
                    "posted" to mapOf("timestampValue" to "2024-01-01T00:00:00Z"),
                    "readme_file" to mapOf("stringValue" to formData.mdFileName),
                    "read_time" to mapOf("integerValue" to calculateReadTime(formData.markdownContent).toString()),
                    "tags" to mapOf(
                        "arrayValue" to mapOf(
                            "values" to formData.tags.map { tag ->
                                mapOf("stringValue" to tag)
                            }
                        )
                    ),
                    "tiny_img" to mapOf("stringValue" to formData.tinyImageUrl),
                    "title" to mapOf("stringValue" to formData.title),
                    "url_str" to mapOf("stringValue" to urlTitle)
                )
            )
            
            // Submit to Firebase
            firebaseRepo.createBlog(Json.encodeToString(blogData)).firstOrNull()?.let { response ->
                when (response) {
                    is Response.SuccessResponse -> {
                        onResult(true, "Blog created successfully!")
                        // Reset form
                        _uiState.value = _uiState.value.copy(
                            formData = BlogFormData(),
                            isSubmitting = false,
                            validationErrors = emptySet()
                        )
                    }
                    is Response.ExceptionResponse -> {
                        onResult(false, "Failed to create blog: ${response.message}")
                        _uiState.value = currentState.copy(isSubmitting = false)
                    }
                    else -> {
                        onResult(false, "Blog submission in progress...")
                        _uiState.value = currentState.copy(isSubmitting = false)
                    }
                }
            } ?: run {
                onResult(false, "Failed to submit blog")
                _uiState.value = currentState.copy(isSubmitting = false)
            }
            
        } catch (e: Exception) {
            Log.e("EditorComponent", "Blog submission failed: ${e.message}")
            onResult(false, "Error: ${e.message}")
            _uiState.value = currentState.copy(isSubmitting = false)
        }
    }
}