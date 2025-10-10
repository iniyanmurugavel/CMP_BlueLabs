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

expect fun setMetaTag(name: String? = null, content: String, property: String? = null)
expect fun setMetaTags(tags: Map<String, String>)
expect fun setOpenGraphTags(
    title: String? = null,
    description: String? = null,
    image: String? = null,
    url: String? = null,
    type: String? = null
)
expect fun setTwitterCardTags(
card: String = "summary_large_image",
title: String? = null,
description: String? = null,
image: String? = null
)
