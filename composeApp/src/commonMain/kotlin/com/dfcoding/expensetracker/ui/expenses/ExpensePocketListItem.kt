package com.dfcoding.expensetracker.ui.expenses

import com.dfcoding.expensetracker.domain.model.ExpenseWithPocket

sealed interface ExpensePocketListItem {

    data class DateHeader(val date: String) : ExpensePocketListItem

    data class ExpensePocketItem(val expensePocket: ExpenseWithPocket) : ExpensePocketListItem
}