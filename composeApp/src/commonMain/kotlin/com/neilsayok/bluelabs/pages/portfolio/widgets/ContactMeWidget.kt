package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import net.thauvin.erik.urlencoder.UrlEncoderUtil

@Composable
fun ContactMeWidget() {

    val uriHandler = LocalUriHandler.current


    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val whatsappMessage by remember { derivedStateOf { "*Subject : ${subject.trim()}*\n\n${message}" } }
    val whatsappLink by remember { derivedStateOf { "https://wa.me/+919051880247?text=${whatsappMessage}" } }
    val mailToLink by remember {
        derivedStateOf {
            "mailto:sayokdeymajumder1998@gmail.com?subject=${
                UrlEncoderUtil.encode(
                    subject.trim()
                )
            }&body=${UrlEncoderUtil.encode(message.trim())}"
        }
    }

    val isEnabled by remember {
        derivedStateOf { subject.isNotBlank() && message.isNotBlank() }
    }

    Card() {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
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

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

                Button(
                    onClick = { uriHandler.openUri(mailToLink) },
                    enabled = isEnabled,
                ) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Email")
                }

                Spacer(modifier = Modifier.size(8.dp))

                Button(
                    onClick = { uriHandler.openUri(whatsappLink) },
                    enabled = isEnabled,
                ) {
                    Icon(imageVector = Icons.Default.Whatsapp, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Whatsapp")
                }

                Spacer(modifier = Modifier.size(8.dp))
                Button(
                    onClick = { uriHandler.openUri("tel:+919051880247") },
                    enabled = true,
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Call")
                }
            }
        }
    }

}
