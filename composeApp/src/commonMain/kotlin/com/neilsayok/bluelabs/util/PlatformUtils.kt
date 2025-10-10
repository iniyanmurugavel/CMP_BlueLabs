package com.neilsayok.bluelabs.util

//interface Platform {
//    val name: String
//}

sealed class Platform(val name: String) {
    data object ANDROID : Platform("Android")
    data object IOS : Platform("IOS")
    data object WEB : Platform("WEB")
    data object DESKTOP : Platform("DESKTOP")
}

expect fun getPlatform(): Platform

expect fun setPageTitle(title: String? = null)