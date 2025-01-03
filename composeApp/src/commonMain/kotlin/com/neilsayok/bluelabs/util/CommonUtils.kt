package com.neilsayok.bluelabs.util

import androidx.compose.ui.graphics.Color


fun isAndroid(): Boolean = getPlatform().name.contains("Android", ignoreCase = true)

internal fun String.snakeCase(): String =
    buildString {
        for (c in this@snakeCase) {
            if (c.isUpperCase() && isNotEmpty()) {
                append('_')
            }

            append(c.lowercaseChar())
        }
    }

