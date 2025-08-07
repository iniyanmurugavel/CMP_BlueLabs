package com.neilsayok.bluelabs.pages.indexer.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.neilsayok.bluelabs.domain.util.Response
import com.neilsayok.bluelabs.pages.indexer.component.IndexerComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexerScreen(component: IndexerComponent) {
    val state by component.uiState.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false))

    var password by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var shouldHideSheet by remember { mutableStateOf(false) }

    // Handle snackbar display
    LaunchedEffect(resultMessage) {
        resultMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            resultMessage = null
        }
    }

    // Handle bottom sheet hiding
    LaunchedEffect(shouldHideSheet) {
        if (shouldHideSheet) {
            bottomSheetScaffoldState.bottomSheetState.hide()
            password = ""
            shouldHideSheet = false
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        sheetPeekHeight = 0.dp,
        sheetContent = {
            // Password Bottom Sheet Content
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Enter Password",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Please enter your password to generate the index:",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextButton(
                        onClick = {
                            shouldHideSheet = true
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                component.handleSubmit(password) { success, message ->
                                    resultMessage = message
                                    shouldHideSheet = true
                                }
                            }
                        }, enabled = password.isNotBlank(), modifier = Modifier.weight(1f)
                    ) {
                        Text("Generate")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Blog Index Generator",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (state.isLoading) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading data...", style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    // Show data status
                    val blogCount =
                        (state.blogResponse as? Response.SuccessResponse)?.data?.documents?.size
                            ?: 0
                    val authorCount =
                        (state.authorResponse as? Response.SuccessResponse)?.data?.documents?.size
                            ?: 0

                    Text(
                        text = "Loaded: $blogCount blogs, $authorCount authors",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    if (state.error != null) {
                        Text(
                            text = "OOPS Something went wrong: ${state.error}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        },
                        enabled = state.blogResponse is Response.SuccessResponse && state.authorResponse is Response.SuccessResponse,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Generate Index")
                    }
                }
            }
        }
    }
}