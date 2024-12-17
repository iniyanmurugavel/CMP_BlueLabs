package com.neilsayok.bluelabs.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

actual fun initKoin(context: Any?): KoinApplication = startKoin {
    modules(appModules())
    androidContext(context as Context)
}