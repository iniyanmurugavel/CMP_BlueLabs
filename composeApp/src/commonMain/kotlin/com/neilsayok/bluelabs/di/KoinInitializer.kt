package com.neilsayok.bluelabs.di

import org.koin.core.KoinApplication
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin

expect fun initKoin(context: Any? = null): KoinApplication