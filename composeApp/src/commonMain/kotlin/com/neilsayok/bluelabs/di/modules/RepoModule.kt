package com.neilsayok.bluelabs.di.modules

import com.neilsayok.bluelabs.domain.firebase.FirebaseRepo
import com.neilsayok.bluelabs.domain.github.GithubRepo
import org.koin.dsl.module


val repoModule = module {
    single { FirebaseRepo(get()) }
    single { GithubRepo(get()) }
}