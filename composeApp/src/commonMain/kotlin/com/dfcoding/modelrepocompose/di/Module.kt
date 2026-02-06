package com.dfcoding.modelrepocompose.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            sharedModule,
            platformModule
        )
    }

expect val platformModule: Module

val sharedModule = module {

    //portfolio
    single {
 //       getPortfolioDatabase(get<RoomDatabase.Builder<PortfolioDatabase>>())
    }

   // singleOf(::PortfolioRepositoryImpl).bind<PortfolioRepository>()
    //single { get<PortfolioDatabase>().portfolioDao() }
   // single { get<PortfolioDatabase>().userBalanceDao() }
    //  viewModel { PortfolioViewModel(get()) }

    //core
//    single<HttpClient> { HttpClientFactory.create(get()) }
//    viewModel { CoinsListViewModel(get(),get()) }
//    singleOf(::GetCoinsListUseCase)
    //   singleOf(::KtorCoinsRemoveDataSource).bind<CoinsRemoteDataSource>()
    //   singleOf(::GetCoinDetailUseCase)
    //   singleOf(::GetCoinPriceHistoryUseCase)

}