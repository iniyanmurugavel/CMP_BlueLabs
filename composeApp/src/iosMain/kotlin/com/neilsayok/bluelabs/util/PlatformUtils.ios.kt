package com.neilsayok.bluelabs.util


actual fun getPlatform(): Platform = Platform.IOS
actual fun setPageTitle(title: String?) {
}

actual fun setMetaTag(name: String?, content: String, property: String?) {
}

actual fun setMetaTags(tags: Map<String, String>) {
}

actual fun setOpenGraphTags(
    title: String?,
    description: String?,
    image: String?,
    url: String?,
    type: String?
) {
}

actual fun setTwitterCardTags(
    card: String,
    title: String?,
    description: String?,
    image: String?
) {
}