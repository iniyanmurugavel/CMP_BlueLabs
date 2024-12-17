@file:OptIn(DelicateCoroutinesApi::class)

package com.neilsayok.bluelabs.di.domain

import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.core.module.Module


expect fun provideNetworkModule(): Module


