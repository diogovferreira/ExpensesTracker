package com.dfcoding.expensetracker.di

import com.dfcoding.expensetracker.data.local.ExpenseDatabaseWrapper
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.database.ExpenseDatabase
import com.dfcoding.expensetracker.ui.addpocket.AddPocketViewModel
import com.dfcoding.expensetracker.ui.list.ExpenseListViewModel
import com.dfcoding.expensetracker.ui.home.HomePocketViewModel
import com.dfcoding.expensetracker.ui.addupdate.AddExpenseViewModel
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
    viewModel { AddExpenseViewModel(get()) }
    viewModel { HomePocketViewModel(get()) }
    viewModel { AddPocketViewModel(get()) }

}

val appModules = listOf(dataModule, viewModelModule)