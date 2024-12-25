package com.neilsayok.bluelabs.di

import com.neilsayok.bluelabs.di.domain.provideNetworkModule
import org.koin.core.module.Module


fun appModules(): List<Module> =
    listOf(
        provideNetworkModule(),
    )




