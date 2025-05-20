package com.neilsayok.bluelabs.util

//interface Platform {
//    val name: String
//}

sealed class Platform(val name : String){
    data object ANDROID : Platform("Android")
    data object IOS : Platform("Android")
    data object WEB : Platform("Android")
    data object DESKTOP : Platform("Android")
}

expect fun getPlatform(): Platform