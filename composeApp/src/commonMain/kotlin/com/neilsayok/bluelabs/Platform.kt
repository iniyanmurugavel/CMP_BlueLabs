package com.neilsayok.bluelabs

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform