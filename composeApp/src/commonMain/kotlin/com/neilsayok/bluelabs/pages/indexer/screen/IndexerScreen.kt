package com.neilsayok.bluelabs.pages.indexer.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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

@Composable
fun IndexerScreen(component: IndexerComponent) {
    val state by component.uiState.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    
    var showPasswordDialog by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
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
                        text = "Loading data...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    // Show data status
                    val blogCount = (state.blogResponse as? Response.SuccessResponse)?.data?.documents?.size ?: 0
                    val authorCount = (state.authorResponse as? Response.SuccessResponse)?.data?.documents?.size ?: 0
                    
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
                        onClick = { showPasswordDialog = true },
                        enabled = state.blogResponse is Response.SuccessResponse && 
                                state.authorResponse is Response.SuccessResponse,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Generate Index")
                    }
                }
            }
        }
    }

    // Password Dialog
    if (showPasswordDialog) {
        AlertDialog(
            onDismissRequest = { 
                showPasswordDialog = false
                password = ""
            },
            title = { Text("Enter Password") },
            text = {
                Column {
                    Text("Please enter your password to generate the index:")
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            component.handleSubmit(password) { success, message ->
                                showPasswordDialog = false
                                password = ""
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(message)
                                }
                            }
                        }
                    },
                    enabled = password.isNotBlank()
                ) {
                    Text("Generate")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        showPasswordDialog = false
                        password = ""
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}