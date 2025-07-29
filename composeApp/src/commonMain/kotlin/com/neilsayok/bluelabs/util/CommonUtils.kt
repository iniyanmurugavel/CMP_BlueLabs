@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.neilsayok.bluelabs.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


internal fun String.snakeCase(): String =
    buildString {
        for (c in this@snakeCase) {
            if (c.isUpperCase() && isNotEmpty()) {
                append('_')
            }

            append(c.lowercaseChar())
        }
    }


@OptIn(ExperimentalTime::class)
fun String.toReadableDate(): String {
    val instant = Instant.parse(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
    val day = localDateTime.dayOfMonth
    val month = localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val year = localDateTime.year

    val dayWithSuffix = when {
        day in 11..13 -> "${day}th"
        day % 10 == 1 -> "${day}st"
        day % 10 == 2 -> "${day}nd"
        day % 10 == 3 -> "${day}rd"
        else -> "${day}th"
    }

    return "$dayWithSuffix $month $year"
}

expect object Log {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String)
}

