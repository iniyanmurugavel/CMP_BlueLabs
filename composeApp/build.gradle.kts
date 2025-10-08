import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

fun localPropertyGetKey(key: String): String =
    gradleLocalProperties(rootDir, providers).getProperty(key)

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildkonfig)
    kotlin("plugin.serialization") version "1.9.10"

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class) compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class) wasmJs {
        outputModuleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }


    sourceSets {
        val desktopMain by getting

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(compose.runtime)
            implementation(compose.foundation)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.vipulasri.kachetor)
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.koin.android)
            implementation(libs.multiplatform.markdown.renderer.android)
            implementation(libs.vipulasri.kachetor)
            implementation(libs.coil.gif)

//            implementation("com.facebook.flipper:flipper:0.182.0")
//            implementation("com.facebook.flipper:flipper-network-plugin:0.182.0")
//            implementation("com.facebook.soloader:soloader:0.10.5")



            implementation("com.facebook.flipper:flipper:0.259.0")
            implementation("com.facebook.soloader:soloader:0.10.5")

            // For OkHttp integration
            implementation("com.facebook.flipper:flipper-network-plugin:0.236.0")

        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(compose.materialIconsExtended)


            implementation(libs.multiplatform.markdown.renderer)
            implementation(libs.multiplatform.markdown.renderer.m3)
            implementation(libs.multiplatform.markdown.renderer.highlights)
            implementation(libs.multiplatform.markdown.renderer.coil3)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.kotlin.serialization)
            runtimeOnly(libs.kotlin.reflect)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            implementation(libs.adaptive)
            implementation(libs.adaptive.layout)
            implementation(libs.adaptive.navigation)

            implementation(compose.material3AdaptiveNavigationSuite)

            implementation(libs.navigation.compose)
            implementation(libs.material.navigation)
            implementation(libs.material3.window.size)

            implementation(libs.arkivanov.decompose)
            implementation(libs.decompose.extensions.compose)
//            implementation(libs.fuzzywuzzy.kotlin)

            implementation("io.github.neilsayok:fuse-kt")


            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.no.arg)

            implementation(libs.kotlinx.datetime)

            implementation(libs.urlencoder.lib)

            //TODO Remove this later
            implementation("de.drick.compose:hotpreview:0.1.6")


            implementation(libs.cryptography.core)
            implementation(libs.cryptography.provider.optimal)

            implementation("org.kodein.emoji:emoji-compose-m3:2.2.0")
            implementation("io.github.neilsayok:fuse-kt:1.0.0")

        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.cio)
            implementation(libs.multiplatform.markdown.renderer.jvm)
            implementation(libs.ktor.client.okhttp)


        }
    }

}

android {
    namespace = "com.neilsayok.bluelabs"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.neilsayok.bluelabs"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.neilsayok.bluelabs.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.neilsayok.bluelabs"
            packageVersion = "1.0.0"
        }
    }
}

buildkonfig {
    packageName = "com.neilsayok.bluelabs"
    defaultConfigs {
        buildConfigField(STRING, "FIREBASE_BASE_URL", localPropertyGetKey("FIREBASE_BASE_URL"))
        buildConfigField(STRING, "GITHUB_TOKEN", localPropertyGetKey("GIT_TOKEN"))
        buildConfigField(STRING, "GITHUB_BASE_URL", localPropertyGetKey("GIT_BASE_URL"))
        buildConfigField(STRING, "FIREBASE_AUTH_TOKEN", localPropertyGetKey("FIREBASE_BEARER"))
        buildConfigField(STRING, "SHA_SECRET_KEY", localPropertyGetKey("SHA_SECRET_KEY"))
        buildConfigField(STRING, "DEBUG_LEVEL", "true")
        buildConfigField(BOOLEAN, "DEBUG", "true")
        buildConfigField(STRING, "BASE_URL", localPropertyGetKey("BASE_URL"))
    }
}

tasks.register<JavaExec>("generateSitemap") {
    group = "build"
    description = "Generate sitemap.xml with dynamic blog data from Firebase"

    val baseUrl = localPropertyGetKey("BASE_URL")
    val firebaseUrl = localPropertyGetKey("FIREBASE_BASE_URL")
    val authToken = localPropertyGetKey("FIREBASE_BEARER")
    val outputPath = file("src/wasmJsMain/resources/sitemap.xml").absolutePath

    mainClass.set("SitemapGeneratorScript")

    // Use buildSrc compiled classes + Kotlin stdlib
    classpath = files(
        "${project.rootProject.projectDir}/buildSrc/build/classes/kotlin/main",
        "${project.rootProject.projectDir}/buildSrc/build/libs/buildSrc.jar"
    ) + buildscript.configurations["classpath"]!!

    args(baseUrl, firebaseUrl, authToken, outputPath)

    doFirst {
        println("🚀 Starting sitemap generation...")
        println("   Base URL: $baseUrl")
        println("   Firebase URL: $firebaseUrl")
        println("   Output: $outputPath")
    }
}

tasks.register("buildWithSitemap") {
    group = "build"
    description = "Generate sitemap, build WASM distribution, and copy sitemap to dist folder"

    dependsOn("generateSitemap", "wasmJsBrowserDistribution")

    doLast {
        val sitemapSource = file("src/wasmJsMain/resources/sitemap.xml")
        val sitemapDest = file("build/dist/wasmJs/productionExecutable/sitemap.xml")

        if (sitemapSource.exists()) {
            sitemapDest.parentFile.mkdirs()
            sitemapSource.copyTo(sitemapDest, overwrite = true)
            println("✅ Sitemap copied to: ${sitemapDest.absolutePath}")
        } else {
            println("⚠️  Sitemap not found at: ${sitemapSource.absolutePath}")
        }
    }
}

tasks.named("wasmJsBrowserDistribution") {
    mustRunAfter("generateSitemap")
}

