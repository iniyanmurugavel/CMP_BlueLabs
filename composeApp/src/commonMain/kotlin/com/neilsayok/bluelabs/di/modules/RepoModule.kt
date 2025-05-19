package com.neilsayok.bluelabs.di.modules

import com.neilsayok.bluelabs.domain.firebase.FirebaseRepo
import org.koin.dsl.module


val repoModule = module {
    single { FirebaseRepo(get()) }
}