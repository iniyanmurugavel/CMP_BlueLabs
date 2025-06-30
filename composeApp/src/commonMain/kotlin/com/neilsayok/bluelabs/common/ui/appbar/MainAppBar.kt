package com.neilsayok.bluelabs.common.ui.appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
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
import com.neilsayok.bluelabs.navigation.NavigationEvent
import com.neilsayok.bluelabs.navigation.RootComponent
import com.neilsayok.bluelabs.util.Platform
import com.neilsayok.bluelabs.util.getPlatform
import com.neilsayok.bluelabs.util.layoutType
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    isDark: Boolean,
    navigate: (NavigationEvent) -> Unit,
    currentScreen: RootComponent.Configuration,
    onThemeChange: (Boolean) -> Unit
) {

    val scope = rememberCoroutineScope()

    var searchKey by remember { mutableStateOf("") }

    val showText = layoutType != NavigationSuiteType.NavigationBar

    val title = if (showText) {
        "BLUE LABS"
    } else {
        "BLUE\nLABS"
    }

    val showBackButton =
        currentScreen != RootComponent.Configuration.HomeScreen && getPlatform() == Platform.DESKTOP

    TopAppBar(
        navigationIcon = {
            AnimatedVisibility(showBackButton, enter = fadeIn(), exit = fadeOut()) {
                IconButton(onClick = { navigate(NavigationEvent.NavigateUp) }) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Go Back")
                }
            }
        },
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.6f).clickable { navigate(NavigationEvent.NavigateHome) },
                    painter = if (isDark) painterResource(Res.drawable.blue_labs_icon_white)
                    else painterResource(Res.drawable.blue_labs_icon),
                    contentDescription = "Bluelabs Icon"
                )
                Text(
                    text = title,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                    ),
                    modifier = Modifier.clickable { navigate(NavigationEvent.NavigateHome) }

                )

                OutlinedTextField(
                    value = searchKey,
                    onValueChange = { searchKey = it },
                    modifier = Modifier.height(48.dp).widthIn(max = 280.dp),
                    textStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search, "Search", tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(),
                    placeholder = {
                        Text(
                            "Search",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Thin,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    })

            }

        },
        actions = {
            if (showText) {
                MainAppBarAction(
                    showText = showText, isDark = isDark, onThemeChange = onThemeChange, navigate = navigate
                )
            } else {
                DropdownMenuWithDetails(isDark = isDark,navigate = navigate) {
                    scope.launch {
                        onThemeChange(it)
                    }
                }
            }

        },


        )


}

@Composable
fun ThemeSwitch(showText: Boolean, isDark: Boolean, onThemeChange: (Boolean) -> Unit) {
    val scope = rememberCoroutineScope()
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


@Composable
fun DropdownMenuWithDetails(isDark: Boolean, navigate: (NavigationEvent) -> Unit,onThemeChange: (Boolean) -> Unit,) {
    var expanded by remember { mutableStateOf(false) }
    val iconButtonColors =
        IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    val menuItemColor = MenuDefaults.itemColors(
        leadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
    Box(
        modifier = Modifier
    ) {
        IconButton(
            onClick = { expanded = !expanded },
            colors = iconButtonColors
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {

            DropdownMenuItem(
                text = { Text("Privacy Policy") },
                colors = menuItemColor,
                leadingIcon = { Icon(Icons.Filled.Policy, contentDescription = null) },
                onClick = { navigate(NavigationEvent.NavigatePrivacyPolicy) })

            DropdownMenuItem(
                text = { Text("About Me") },
                colors = menuItemColor,
                leadingIcon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                onClick = { navigate(NavigationEvent.NavigatePortfolio) })

            DropdownMenuItem(
                text = { Text("Switch Theme") },
                colors = menuItemColor,
                leadingIcon = { ThemeIcon(isDark) },
                onClick = { onThemeChange(!isDark) })


        }
    }
}


@Composable
fun RowScope.MainAppBarAction(
    showText: Boolean, isDark: Boolean, onThemeChange: (Boolean) -> Unit, navigate: (NavigationEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    TextButton(onClick = {navigate(NavigationEvent.NavigatePrivacyPolicy)}) {
        Icon(Icons.Filled.Policy, "Privacy Policy")
        if (showText) {
            Text("Privacy Policy")
        }
    }

    TextButton(onClick = { navigate(NavigationEvent.NavigatePortfolio)}) {
        Icon(Icons.Filled.AccountCircle, "About Me")
        if (showText) {
            Text("About Me")
        }
    }


    ThemeSwitch(
        showText = showText, isDark = isDark, onThemeChange = {
            scope.launch {
                onThemeChange(it)
            }
        })
}


