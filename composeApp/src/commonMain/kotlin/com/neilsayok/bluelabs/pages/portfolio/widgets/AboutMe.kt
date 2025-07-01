package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler

@Composable
fun AboutMe() {
    val uriHandler = LocalUriHandler.current

    val fileId = "1iCpE7Fr72y9SwoZuKQqOVyEZg2MeREgoiKpEJ3BkN0Q"
    val resume = "https://docs.google.com/document/d/${fileId}/export?format=pdf"
    Column {
        Text("\uD83D\uDC4B Hey there! I’m Sayok Dey Majumder, a Google Certified Android Developer who believes that good code should be as well-injected as my morning coffee ☕\uFE0E. I’m passionate about building apps that are smoother than a perfectly resolved dependency tree and more reliable than my singleton instances. Whether I’m refactoring code or making sure my modules are loosely coupled (unlike my attachment to my keyboard), I’m always on a mission to ensure my apps don’t just work—they wow. And when I’m not coding, you’ll find me pondering why life didn’t come with dependency injection—would’ve made finding the right coffee easier!")
        Row {
            Button(
                onClick = {uriHandler.openUri(resume)},
            ){
                Text("Download Resume")
            }

            Button(
                onClick = {},
            ){
                Text("Contact Me")
            }
        }
    }
}