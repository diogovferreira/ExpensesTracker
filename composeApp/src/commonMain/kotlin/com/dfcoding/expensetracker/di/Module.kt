package com.dfcoding.expensetracker.di

import com.dfcoding.expensetracker.data.local.ExpenseDatabaseWrapper
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.database.ExpenseDatabase
import com.dfcoding.expensetracker.ui.list.ExpenseListViewModel
import com.dfcoding.expensetracker.ui.pockets.PocketListViewModel
import com.dfcoding.expensetracker.ui.screens.addedit.AddExpenseViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

val dataModule = module {

    single { ExpenseDatabase(get()) }
    single { ExpenseDatabaseWrapper(get()) }
    single { ExpenseRepository(get()) }

}

val viewModelModule = module {
    viewModel { ExpenseListViewModel(get()) }
    viewModel { (expenseId: Long?) -> AddExpenseViewModel(get(), expenseId) }
    viewModel { PocketListViewModel(get()) }

}

val appModules = listOf(dataModule, viewModelModule)