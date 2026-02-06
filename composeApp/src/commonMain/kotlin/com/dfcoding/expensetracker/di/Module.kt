package com.dfcoding.expensetracker.di

import com.dfcoding.expensetracker.data.local.ExpenseDatabaseWrapper
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.database.ExpenseDatabase
import com.dfcoding.expensetracker.ui.list.ExpenseListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect val platformModule: Module

val dataModule = module {

    single { ExpenseDatabase(get()) }
    single { ExpenseDatabaseWrapper(get()) }
    single { ExpenseRepository(get()) }

}

val viewModelModule = module {
    viewModel {
        ExpenseListViewModel(get())
    }
}

val appModules = listOf(dataModule, viewModelModule)