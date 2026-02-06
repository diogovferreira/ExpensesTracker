package com.dfcoding.expensetracker.di

import org.koin.core.context.startKoin
fun initKoin() {
    startKoin {
        modules(platformModule + appModules)
    }
}