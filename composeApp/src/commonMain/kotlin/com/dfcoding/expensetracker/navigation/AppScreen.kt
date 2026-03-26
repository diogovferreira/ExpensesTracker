package com.dfcoding.expensetracker.navigation

import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.Pocket

sealed class AppScreen{
    object Home: AppScreen()
    object Stats: AppScreen()
    object History: AppScreen()
    object Settings: AppScreen()
    data class AddPocket(val pocket: Pocket? = null): AppScreen()
    data class AddExpense(val expense: Expense? = null): AppScreen()
    data class ExpenseList(val pocket: Pocket): AppScreen()
}
