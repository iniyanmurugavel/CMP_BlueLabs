@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.neilsayok.bluelabs.util


actual object Log{
    actual fun d(tag: String, message: String) {
        android.util.Log.d(tag, message)
    }

    actual fun e(tag: String, message: String) {
        android.util.Log.e(tag, message)
    }

    actual fun i(tag: String, message: String) {
        android.util.Log.i(tag, message)
    }

    actual fun w(tag: String, message: String) {
        android.util.Log.w(tag, message)
    }
}