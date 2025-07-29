package com.neilsayok.bluelabs.di.domain

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

val logFile = File("/Users/neil/Downloads/ktor_logs.txt")
val logWriter = logFile.printWriter()

actual fun provideNetworkModule(): Module {
    return module {
        single {
            HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
                install(HttpCache) {
                    val cacheFile = Files.createDirectories(Paths.get("build/cache")).toFile()
                    publicStorage(FileStorage(cacheFile))
                }

//                install(Logging) {
//                    logger = object : Logger {
//                        override fun log(message: String) {
//                            println("=".repeat(25))
//                            println("New Request/Response")
//                            println("[HTTP] $message")
//
//                        }
//                    }
//                    level = LogLevel.ALL // Logs request + response bodies, headers, etc.
//                }
            }
        }

    }
}