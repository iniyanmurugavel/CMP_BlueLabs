package com.neilsayok.bluelabs.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val BackgroundDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO