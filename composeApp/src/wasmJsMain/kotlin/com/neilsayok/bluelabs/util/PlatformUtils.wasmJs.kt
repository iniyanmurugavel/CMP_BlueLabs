package com.neilsayok.bluelabs.util

import kotlinx.browser.document


actual fun getPlatform(): Platform = Platform.WEB

actual fun setPageTitle(title: String?) {
    if (title == null) document.title = "BlueLabs"
    else document.title = title
}