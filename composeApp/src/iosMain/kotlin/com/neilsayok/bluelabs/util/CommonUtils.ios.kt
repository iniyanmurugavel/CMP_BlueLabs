@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.neilsayok.bluelabs.util

import platform.Foundation.NSLog

actual object Log{
    actual fun d(tag: String, message: String) {
        NSLog("D/$tag: $message")
    }

    actual fun e(tag: String, message: String) {
        NSLog("I/$tag: $message")
    }

    actual fun i(tag: String, message: String) {
        NSLog("W/$tag: $message")
    }

    actual fun w(tag: String, message: String) {
        NSLog("E/$tag: $message")
    }
}