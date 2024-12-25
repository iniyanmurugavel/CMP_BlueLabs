package com.neilsayok.bluelabs

import com.neilsayok.bluelabs.util.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}