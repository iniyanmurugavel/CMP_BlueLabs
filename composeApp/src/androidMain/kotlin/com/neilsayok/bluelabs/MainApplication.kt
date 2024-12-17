package com.neilsayok.bluelabs

import android.app.Application
import com.neilsayok.bluelabs.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(applicationContext)
    }
}