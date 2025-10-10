package com.neilsayok.bluelabs.util

import kotlinx.browser.document
import org.w3c.dom.HTMLMetaElement


actual fun getPlatform(): Platform = Platform.WEB

actual fun setPageTitle(title: String?) {
    if (title == null) document.title = "BlueLabs"
    else document.title = title
}

/**
 * Injects or updates a meta tag in the document head
 * @param name The name attribute of the meta tag
 * @param content The content attribute of the meta tag
 * @param property Optional property attribute (used for Open Graph tags like og:title)
 */
actual fun setMetaTag(name: String?, content: String, property: String?) {
    val head = document.head ?: return

    // Try to find existing meta tag
    val selector = when {
        property != null -> "meta[property='$property']"
        name != null -> "meta[name='$name']"
        else -> return
    }

    val existingMeta = document.querySelector(selector) as? HTMLMetaElement

    if (existingMeta != null) {
        // Update existing meta tag
        existingMeta.content = content
    } else {
        // Create new meta tag
        val metaTag = document.createElement("meta") as HTMLMetaElement
        if (name != null) metaTag.name = name
        if (property != null) metaTag.setAttribute("property", property)
        metaTag.content = content
        head.appendChild(metaTag)
    }
}

/**
 * Sets multiple meta tags at once
 */
actual fun setMetaTags(tags: Map<String, String>) {
    tags.forEach { (key, value) ->
        setMetaTag(name = key, content = value)
    }
}

/**
 * Sets Open Graph meta tags
 */
actual fun setOpenGraphTags(
    title: String?,
    description: String?,
    image: String?,
    url: String?,
    type: String?
) {
    title?.let { setMetaTag(property = "og:title", content = it) }
    description?.let { setMetaTag(property = "og:description", content = it) }
    image?.let { setMetaTag(property = "og:image", content = it) }
    url?.let { setMetaTag(property = "og:url", content = it) }
    type?.let { setMetaTag(property = "og:type", content = it) }
}

/**
 * Sets Twitter Card meta tags
 */
actual fun setTwitterCardTags(
    card: String,
    title: String?,
    description: String?,
    image: String?
) {
    setMetaTag(name = "twitter:card", content = card)
    title?.let { setMetaTag(name = "twitter:title", content = it) }
    description?.let { setMetaTag(name = "twitter:description", content = it) }
    image?.let { setMetaTag(name = "twitter:image", content = it) }
}