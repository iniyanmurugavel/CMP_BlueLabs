@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.neilsayok.bluelabs.util


actual object Log{
    actual fun d(tag: String, message: String) {
        js("console.log(tag,message)")
    }

    actual fun e(tag: String, message: String) {
        js("console.error(tag,message)")
    }

    actual fun i(tag: String, message: String) {
        js("console.info(tag,message)")
    }

    actual fun w(tag: String, message: String) {
        js("console.warn(tag,message)")
    }
}