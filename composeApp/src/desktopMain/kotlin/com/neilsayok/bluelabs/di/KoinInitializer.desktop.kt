package com.neilsayok.bluelabs.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

actual fun initKoin(context: Any?): KoinApplication = startKoin {
    modules(appModules())
}