@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.neilsayok.bluelabs.util

import androidx.compose.runtime.Composable
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.HMAC
import dev.whyoleg.cryptography.algorithms.SHA256
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


@Composable fun loadImage(url : String?) : AsyncImagePainter = rememberAsyncImagePainter(
    model = url,
    placeholder = null,
    error = null,
    fallback = null
)

expect object Log {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String)
}

/**
 * Converts HTML/markdown text to plain text by removing HTML tags, formatting, special characters,
 * duplicate words, and converting to lowercase
 */
fun convertToPlainText(text: String): String {
    val processed = text
        // Remove HTML tags
        .replace(Regex("<[^>]*>"), " ")
        // Convert markdown links to text (keep link text, remove URL)
        .replace(Regex("\\[([^\\]]+)\\]\\([^\\)]+\\)"), "$1")
        // Remove markdown formatting
        .replace(Regex("\\*\\*([^\\*]+)\\*\\*"), "$1") // Bold
        .replace(Regex("\\*([^\\*]+)\\*"), "$1") // Italic
        .replace(Regex("__([^_]+)__"), "$1") // Bold (alternative)
        .replace(Regex("_([^_]+)_"), "$1") // Italic (alternative)
        .replace(Regex("~~([^~]+)~~"), "$1") // Strikethrough
        // Remove markdown headers
        .replace(Regex("#+\\s*"), "")
        // Remove code blocks and inline code
        .replace(Regex("```[\\s\\S]*?```"), " ") // Code blocks (multiline)
        .replace(Regex("`([^`]+)`"), "$1") // Inline code
        // Remove markdown lists
        .replace(Regex("^\\s*[*+-]\\s+", RegexOption.MULTILINE), "")
        .replace(Regex("^\\s*\\d+\\.\\s+", RegexOption.MULTILINE), "")
        // Remove blockquotes
        .replace(Regex("^\\s*>\\s*", RegexOption.MULTILINE), "")
        // Remove horizontal rules
        .replace(Regex("^\\s*[-*_]{3,}\\s*$", RegexOption.MULTILINE), "")
        // Remove tables (basic cleanup)
        .replace(Regex("\\|"), " ")
        // Remove special characters and symbols
        .replace(Regex("[!@#$%^&*()_+=\\[\\]{}\\\\|;:'\",.<>?/~`]"), " ")
        // Remove extra punctuation and smart quotes using unicode escape sequences
        .replace(Regex("[\\u201C\\u201D]"), " ") // Smart double quotes
        .replace(Regex("[\\u2018\\u2019]"), " ") // Smart single quotes
        .replace(Regex("[\\u2013\\u2014]"), " ") // Em and en dashes
        .replace(Regex("[\\u2026]"), " ") // Ellipsis
        .replace(Regex("[\\u00A0]"), " ") // Non-breaking space
        // Remove non-printable characters and control characters
        .replace(Regex("[\\x00-\\x1F\\x7F-\\x9F]"), " ")
        // Normalize whitespace
        .replace(Regex("\\s+"), " ")
        .trim()
        // Convert to lowercase
        .lowercase()
    
    // Remove duplicate words while preserving order
    return removeDuplicateWords(processed)
}

/**
 * Removes duplicate words from a string while preserving the order of first occurrences
 */
private fun removeDuplicateWords(text: String): String {
    if (text.isBlank()) return text
    
    val words = text.split(Regex("\\s+"))
    val seenWords = mutableSetOf<String>()
    val uniqueWords = mutableListOf<String>()
    
    for (word in words) {
        val cleanWord = word.trim()
        if (cleanWord.isNotEmpty() && !seenWords.contains(cleanWord)) {
            seenWords.add(cleanWord)
            uniqueWords.add(cleanWord)
        }
    }
    
    return uniqueWords.joinToString(" ")
}

/**
 * HMAC SHA-256 hash implementation matching the JavaScript version
 * Uses cryptography-kotlin library for proper cryptographic operations
 */
suspend fun sha256Hash(input: String, secretKey: String = "your-secret-key"): String {
    return try {
        val provider = CryptographyProvider.Default
        val hmac = provider.get(HMAC)
        
        // Create HMAC-SHA256 key from raw bytes
        val keyBytes = secretKey.encodeToByteArray()
        val key = hmac.keyDecoder(SHA256).decodeFromByteArray(HMAC.Key.Format.RAW, keyBytes)
        
        // Generate HMAC signature
        val inputBytes = input.encodeToByteArray()
        val signature = key.signatureGenerator().generateSignature(inputBytes)
        
        // Convert signature bytes to hex string
        signature.joinToString("") { byte -> 
            (byte.toInt() and 0xFF).toString(16).padStart(2, '0')
        }
    } catch (e: Exception) {
        // Fallback to simple hash if crypto fails - log the error for debugging
        println("HMAC generation failed: ${e.message}")
        input.hashCode().toString(16).padStart(8, '0')
    }
}
