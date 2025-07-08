package com.neilsayok.bluelabs.di.domain

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.neilsayok.bluelabs.BuildKonfig
import com.vipulasri.kachetor.KachetorStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

fun provideFlipperNetworkInterceptor(context: Context): FlipperOkhttpInterceptor? {
    return if (BuildKonfig.DEBUG) {
        val client = AndroidFlipperClient.getInstance(context)

        // Get existing NetworkFlipperPlugin or create new one
        val networkFlipperPlugin = client.getPluginByClass(NetworkFlipperPlugin::class.java)
            ?: NetworkFlipperPlugin().also { client.addPlugin(it) }

        FlipperOkhttpInterceptor(networkFlipperPlugin)
    } else {
        null
    }
}

actual fun provideNetworkModule(): Module {
    return module {
        single {
            val context = get<Context>()
            val flipperInterceptor = provideFlipperNetworkInterceptor(context)

            HttpClient(OkHttp) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
                install(HttpCache) {
                    publicStorage(KachetorStorage(10 * 1024 * 1024)) // 10MB
                }
                
                engine {
                    flipperInterceptor?.let {
                        addInterceptor(it)
                    }
                }
            }
        }
    }
}