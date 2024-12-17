package com.neilsayok.bluelabs.util

import com.neilsayok.bluelabs.getPlatform

fun isAndroid(): Boolean = getPlatform().name.contains("Android", ignoreCase = true)

