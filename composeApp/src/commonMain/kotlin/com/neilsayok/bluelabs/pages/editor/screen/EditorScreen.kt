package com.neilsayok.bluelabs.pages.editor.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.pages.editor.component.EditorComponent
import com.neilsayok.bluelabs.pages.editor.component.state.ValidationError
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditorScreen(component: EditorComponent) {
    val state by component.uiState.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        rememberStandardBottomSheetState(skipHiddenState = false)
    )
    
    var password by remember { mutableStateOf("") }
    var tagInput by remember { mutableStateOf("") }
    var genreExpanded by remember { mutableStateOf(false) }
    var authorExpanded by remember { mutableStateOf(false) }
    
    // Handle snackbar messages
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            snackbarMessage = null
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        sheetPeekHeight = 0.dp,
        sheetContent = {
            // Password Bottom Sheet
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Submit Blog",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Please enter your password to submit the blog:")
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.hide()
                                password = ""
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                component.submitBlog(password) { success, message ->
                                    snackbarMessage = message
                                    if (success) {
                                        password = ""
                                    }
                                }
                                bottomSheetScaffoldState.bottomSheetState.hide()
                            }
                        },
                        enabled = password.isNotBlank() && !state.isSubmitting,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (state.isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Submit")
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) { paddingValues ->
        
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading editor data...")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Blog Editor",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    
                    Text(
                        text = "All fields marked with * are mandatory",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Genre Selection
                item {
                    val genres = (state.genreResponse as? Response.SuccessResponse)?.data?.documents ?: emptyList()
                    
                    ExposedDropdownMenuBox(
                        expanded = genreExpanded,
                        onExpandedChange = { genreExpanded = !genreExpanded }
                    ) {
                        OutlinedTextField(
                            value = genres.find { it?.name?.substringAfterLast("/") == state.formData.selectedGenre }?.fields?.type?.stringValue ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Genre *") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genreExpanded) },
                            isError = ValidationError.GENRE_EMPTY in state.validationErrors,
                            supportingText = if (ValidationError.GENRE_EMPTY in state.validationErrors) {
                                { Text("Genre is required") }
                            } else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        
                        ExposedDropdownMenu(
                            expanded = genreExpanded,
                            onDismissRequest = { genreExpanded = false }
                        ) {
                            genres.forEach { genre ->
                                genre?.let {
                                    val genreId = it.name?.substringAfterLast("/") ?: ""
                                    val genreType = it.fields?.type?.stringValue ?: ""
                                    
                                    DropdownMenuItem(
                                        text = { Text(genreType) },
                                        onClick = {
                                            component.updateFormData(
                                                state.formData.copy(selectedGenre = genreId)
                                            )
                                            genreExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Image URLs
                item {
                    OutlinedTextField(
                        value = state.formData.tinyImageUrl,
                        onValueChange = { 
                            component.updateFormData(
                                state.formData.copy(tinyImageUrl = it)
                            )
                        },
                        label = { Text("Small Image URL *") },
                        isError = ValidationError.TINY_IMAGE_URL_EMPTY in state.validationErrors,
                        supportingText = if (ValidationError.TINY_IMAGE_URL_EMPTY in state.validationErrors) {
                            { Text("Small image URL is required") }
                        } else null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                item {
                    OutlinedTextField(
                        value = state.formData.bigImageUrl,
                        onValueChange = { 
                            component.updateFormData(
                                state.formData.copy(bigImageUrl = it)
                            )
                        },
                        label = { Text("Header Image URL *") },
                        isError = ValidationError.BIG_IMAGE_URL_EMPTY in state.validationErrors,
                        supportingText = if (ValidationError.BIG_IMAGE_URL_EMPTY in state.validationErrors) {
                            { Text("Header image URL is required") }
                        } else null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Title
                item {
                    OutlinedTextField(
                        value = state.formData.title,
                        onValueChange = { 
                            if (it.length <= 2000) {
                                component.updateFormData(
                                    state.formData.copy(title = it)
                                )
                            }
                        },
                        label = { Text("Title *") },
                        isError = ValidationError.TITLE_EMPTY in state.validationErrors || 
                                 ValidationError.TITLE_TOO_LONG in state.validationErrors,
                        supportingText = {
                            when {
                                ValidationError.TITLE_EMPTY in state.validationErrors -> 
                                    Text("Title is required")
                                ValidationError.TITLE_TOO_LONG in state.validationErrors -> 
                                    Text("Title is too long")
                                else -> Text("Characters left: ${2000 - state.formData.title.length}")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Tags
                item {
                    Column {
                        OutlinedTextField(
                            value = tagInput,
                            onValueChange = { tagInput = it },
                            label = { Text("Tags (Press Enter to add) *") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (tagInput.isNotBlank()) {
                                        component.addTag(tagInput.trim())
                                        tagInput = ""
                                    }
                                }
                            ),
                            isError = ValidationError.TAGS_EMPTY in state.validationErrors ||
                                     ValidationError.TAGS_TOO_MANY in state.validationErrors,
                            supportingText = {
                                when {
                                    ValidationError.TAGS_EMPTY in state.validationErrors -> 
                                        Text("At least one tag is required")
                                    ValidationError.TAGS_TOO_MANY in state.validationErrors -> 
                                        Text("Maximum 4 tags allowed")
                                    else -> Text("Minimum 1, Maximum 4 tags")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            state.formData.tags.forEachIndexed { index, tag ->
                                AssistChip(
                                    onClick = { component.removeTag(index) },
                                    label = { Text(tag) },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Remove tag",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                // Markdown File
                item {
                    OutlinedTextField(
                        value = state.formData.mdFileName,
                        onValueChange = { fileName ->
                            component.updateFormData(
                                state.formData.copy(mdFileName = fileName)
                            )
                            if (fileName.endsWith(".md")) {
                                component.loadMarkdownFromFile(fileName)
                            }
                        },
                        label = { Text("Markdown File Name *") },
                        isError = ValidationError.MD_FILE_EMPTY in state.validationErrors,
                        supportingText = if (ValidationError.MD_FILE_EMPTY in state.validationErrors) {
                            { Text("Markdown file name is required") }
                        } else { 
                            { Text("Enter filename ending with .md to auto-load content") }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Author Selection
                item {
                    val authors = (state.authorResponse as? Response.SuccessResponse)?.data?.documents ?: emptyList()
                    
                    ExposedDropdownMenuBox(
                        expanded = authorExpanded,
                        onExpandedChange = { authorExpanded = !authorExpanded }
                    ) {
                        OutlinedTextField(
                            value = authors.find { it?.name?.substringAfterLast("/") == state.formData.selectedAuthor }?.let { author ->
                                "${author.fields?.name?.stringValue ?: ""} (${author.fields?.uid?.stringValue ?: ""})"
                            } ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Author *") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = authorExpanded) },
                            isError = ValidationError.AUTHOR_EMPTY in state.validationErrors,
                            supportingText = if (ValidationError.AUTHOR_EMPTY in state.validationErrors) {
                                { Text("Author is required") }
                            } else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        
                        ExposedDropdownMenu(
                            expanded = authorExpanded,
                            onDismissRequest = { authorExpanded = false }
                        ) {
                            authors.forEach { author ->
                                author?.let {
                                    val authorId = it.name?.substringAfterLast("/") ?: ""
                                    val authorName = it.fields?.name?.stringValue ?: ""
                                    val authorUid = it.fields?.uid?.stringValue ?: ""
                                    
                                    DropdownMenuItem(
                                        text = { Text("$authorName ($authorUid)") },
                                        onClick = {
                                            component.updateFormData(
                                                state.formData.copy(selectedAuthor = authorId)
                                            )
                                            authorExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Read Time Display
                if (state.formData.markdownContent.isNotBlank()) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(
                                text = "Read Time: ${component.calculateReadTime(state.formData.markdownContent)} mins",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                // Preview Section
                if (state.formData.tinyImageUrl.isNotBlank() || state.formData.bigImageUrl.isNotBlank()) {
                    item {
                        Text(
                            text = "Preview",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Small Image Preview
                if (state.formData.tinyImageUrl.isNotBlank()) {
                    item {
                        Text("Small Image Preview:")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            repeat(3) { index ->
                                Card(
                                    modifier = Modifier.size(80.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Gray.copy(alpha = 0.1f)
                                    )
                                ) {
                                    AsyncImage(
                                        model = state.formData.tinyImageUrl,
                                        contentDescription = "Small image preview",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }

                // Big Image Preview
                if (state.formData.bigImageUrl.isNotBlank()) {
                    item {
                        AsyncImage(
                            model = state.formData.bigImageUrl,
                            contentDescription = "Header image preview",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }

                // Title Preview
                if (state.formData.title.isNotBlank()) {
                    item {
                        Text(
                            text = state.formData.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Tags Preview
                if (state.formData.tags.isNotEmpty()) {
                    item {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            state.formData.tags.forEach { tag ->
                                AssistChip(
                                    onClick = { },
                                    label = { Text(tag) }
                                )
                            }
                        }
                    }
                }

                // Submit Button
                item {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        },
                        enabled = !state.isSubmitting && state.validationErrors.isEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text("Submit Blog")
                    }
                }
                
                // Spacer for bottom padding
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}