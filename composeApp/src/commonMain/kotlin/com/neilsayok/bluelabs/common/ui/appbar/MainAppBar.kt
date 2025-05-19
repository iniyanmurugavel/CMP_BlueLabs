package com.neilsayok.bluelabs.common.ui.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bluelabscmp.composeapp.generated.resources.Res
import bluelabscmp.composeapp.generated.resources.blue_labs_icon
import bluelabscmp.composeapp.generated.resources.blue_labs_icon_white
import com.neilsayok.bluelabs.util.layoutType
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(isDark: Boolean, onThemeChange: (Boolean) -> Unit) {

    val scope = rememberCoroutineScope()

    var searchKey by remember { mutableStateOf("") }

    val showText = layoutType != NavigationSuiteType.NavigationBar


    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.6f),
                    painter = if (isDark) painterResource(Res.drawable.blue_labs_icon_white)
                    else painterResource(Res.drawable.blue_labs_icon),
                    contentDescription = "Bluelabs Icon"
                )
                Text(
                    "BLUE LABS", style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp
                    )
                )

                OutlinedTextField(
                    value = searchKey,
                    onValueChange = { searchKey = it },
                    modifier = Modifier.height(48.dp),
                    textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search, "Search", tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(),
                    placeholder = { Text("Search", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Thin) })

            }

        },
        actions = {
            TextButton(onClick = {}) {
                Icon(Icons.Filled.Policy, "Privacy Policy")
                if (showText) {
                    Text("Privacy Policy")
                }
            }

            TextButton(onClick = {}) {
                Icon(Icons.Filled.AccountCircle, "About Me")
                if (showText) {
                    Text("About Me")
                }
            }


            if (showText) {
                Switch(
                    checked = isDark, colors = SwitchDefaults.colors().copy(
                    checkedThumbColor = Color.Transparent,
                    uncheckedThumbColor = Color.Transparent,
                ), onCheckedChange = {
                    scope.launch {
                        onThemeChange(!isDark)
                    }
                }, thumbContent = {
                    ThemeIcon(isDark)
                })
            } else {
                IconButton(onClick = {
                    scope.launch {
                        onThemeChange(!isDark)
                    }
                }) {
                    ThemeIcon(isDark)
                }
            }

        },


        )


}

@Composable
fun ThemeIcon(isDark: Boolean) {
    AnimatedVisibility(
        !isDark, enter = fadeIn(), exit = fadeOut()
    ) {
        Icon(
            Icons.Default.LightMode, "DarkMode", tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
    AnimatedVisibility(
        isDark, enter = fadeIn(), exit = fadeOut()
    ) {
        Icon(
            Icons.Default.DarkMode, "DarkMode", tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}


