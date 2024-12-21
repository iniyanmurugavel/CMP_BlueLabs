package com.neilsayok.bluelabs.util


fun isAndroid(): Boolean = getPlatform().name.contains("Android", ignoreCase = true)

