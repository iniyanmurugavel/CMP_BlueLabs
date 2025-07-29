@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.neilsayok.bluelabs.util

const val ANSI_RESET = "\u001B[0m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"

actual object Log{
    actual fun d(tag: String, message: String) {
        println("$ANSI_GREEN $tag: $message $ANSI_RESET")
    }

    actual fun e(tag: String, message: String) {
        println("$ANSI_RED $tag: $message $ANSI_RESET")

    }

    actual fun i(tag: String, message: String) {
        println("$ANSI_BLUE $tag: $message $ANSI_RESET")

    }

    actual fun w(tag: String, message: String) {
        println("$ANSI_YELLOW $tag: $message $ANSI_RESET")

    }
}