package com.dfcoding.modelrepocompose.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    //core
    single<HttpClientEngine> { Android.create()}

   // singleOf(::getPortfolioDatabaseBuilder).bind<RoomDatabase.Builder<PortfolioDatabase>>()
}