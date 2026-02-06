package com.dfcoding.expensetracker.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class ExpenseListViewModel(
    private val repository: ExpenseRepository
) : ViewModel(){

    val expenses: StateFlow<List<Expense>> = repository.getAllExpenses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTestExpense(){
        viewModelScope.launch {
            val testExpense = Expense(
                id = 0,
                amount = 100.0,
                category = ExpenseCategory.FOOD,
                date = Clock.System.now().toEpochMilliseconds(),
                description = "This is a test expense"
            )
            repository.addExpense(testExpense)

        }
    }

    fun deleteExpense(id: Long) {
        viewModelScope.launch {
            repository.deleteExpense(id)
        }
    }

    // Add multiple test expenses
    fun addMultipleTestExpenses() {
        viewModelScope.launch {
            listOf(
                Expense(0, 45.00, ExpenseCategory.FOOD, "Dinner", Clock.System.now().toEpochMilliseconds()),
                Expense(0, 12.50, ExpenseCategory.TRANSPORT, "Uber", Clock.System.now().toEpochMilliseconds()),
                Expense(0, 89.99, ExpenseCategory.SHOPPING, "New shoes", Clock.System.now().toEpochMilliseconds()),
                Expense(0, 15.00, ExpenseCategory.ENTERTAINMENT, "Movie tickets", Clock.System.now().toEpochMilliseconds())
            ).forEach { expense ->
                repository.addExpense(expense)
            }
        }
    }
}