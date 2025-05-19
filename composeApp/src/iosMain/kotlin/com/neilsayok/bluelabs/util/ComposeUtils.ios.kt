package com.neilsayok.bluelabs.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
actual val BackgroundDispatcher: CoroutineDispatcher
    get() = newSingleThreadContext("iOSDispatcher")