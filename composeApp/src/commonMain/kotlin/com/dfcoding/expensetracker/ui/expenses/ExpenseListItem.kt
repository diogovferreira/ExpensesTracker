package com.dfcoding.expensetracker.ui.expenses

import com.dfcoding.expensetracker.domain.model.Expense

sealed interface ExpenseListItem {
    data class DateHeader(val date: String) : ExpenseListItem
    data class ExpenseEntry(val expense: Expense) : ExpenseListItem
}
