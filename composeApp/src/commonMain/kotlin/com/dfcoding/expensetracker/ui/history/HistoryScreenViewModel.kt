package com.dfcoding.expensetracker.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepositoryInterface
import com.dfcoding.expensetracker.domain.model.ExpenseWithPocket
import com.dfcoding.expensetracker.ui.expenses.ExpensePocketListItem
import com.dfcoding.expensetracker.util.Formatter
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryScreenViewModel(private val repository: ExpenseRepositoryInterface) : ViewModel() {

    val expensesWithPocketInfo: StateFlow<List<ExpenseWithPocket>> = repository.getExpenseWithPocket().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val groupedExpenses: StateFlow<List<ExpensePocketListItem>> =
        expensesWithPocketInfo.map { it.groupedByDate() }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun List<ExpenseWithPocket>.groupedByDate(): List<ExpensePocketListItem> {
        return groupBy { Formatter.formatDateHeader(it.date) }
            .flatMap { (date, expenses) ->
                listOf(ExpensePocketListItem.DateHeader(date)) +
                        expenses.map { ExpensePocketListItem.ExpensePocketItem(it) }
            }
    }
}