package com.neilsayok.bluelabs.util

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform