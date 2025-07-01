package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContactMeWidget() {

    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Card() {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth().heightIn(150.dp)
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                ExtendedFloatingActionButton(
                    onClick = {},
                    content = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Email")
                    },
                )

                Spacer(modifier = Modifier.size(8.dp))

                ExtendedFloatingActionButton(
                    onClick = {},
                    content = {
                        Icon(imageVector = Icons.Default.Whatsapp, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Whatsapp")
                    },
                )
                Spacer(modifier = Modifier.size(8.dp))
                ExtendedFloatingActionButton(
                    onClick = {},
                    content = {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Call")
                    },
                )
            }
        }
    }

}