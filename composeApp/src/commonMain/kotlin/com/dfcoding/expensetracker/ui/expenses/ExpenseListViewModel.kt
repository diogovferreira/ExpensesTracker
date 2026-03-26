package com.dfcoding.expensetracker.ui.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.util.Formatter
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseListViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    val expenses: StateFlow<List<Expense>> = repository.getAllExpenses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val groupedExpenses: StateFlow<List<ExpenseListItem>> =
        expenses.map { it.groupedByDate() }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteExpense(id: Long) {
        viewModelScope.launch {
            repository.deleteExpense(id)
        }
    }


    fun List<Expense>.groupedByDate(): List<ExpenseListItem> {
        return groupBy { Formatter.formatDateHeader(it.date) }
            .flatMap { (date, expenses) ->
                listOf(ExpenseListItem.DateHeader(date)) +
                        expenses.map { ExpenseListItem.ExpenseEntry(it) }
            }
    }
}