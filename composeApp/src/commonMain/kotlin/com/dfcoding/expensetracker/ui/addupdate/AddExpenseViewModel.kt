package com.dfcoding.expensetracker.ui.screens.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class AddExpenseViewModel(
    private val repository: ExpenseRepository,
    private val expenseId: Long? = null
) : ViewModel() {

    private val _expense = MutableStateFlow<Expense?>(null)
    val expense: StateFlow<Expense?> = _expense.asStateFlow()

    init {
        expenseId?.let { id ->
            viewModelScope.launch {
                repository.getExpenseById(id).collect {
                    _expense.value = it
                }
            }
        }
    }

    fun saveExpense(
        amount: Double,
        category: ExpenseCategory,
        description: String
    ) {
        viewModelScope.launch {
            val expense = Expense(
                id = expenseId ?: 0,
                amount = amount,
                category = category,
                description = description,
                date = Clock.System.now().toEpochMilliseconds()
            )

            if (expenseId != null) {
                repository.updateExpense(expense)
            } else {
                repository.addExpense(expense)
            }
        }
    }
}