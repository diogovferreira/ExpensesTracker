package com.dfcoding.expensetracker.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {

    // singleOf(::getPortfolioDatabaseBuilder).bind<RoomDatabase.Builder<PortfolioDatabase>>()
}