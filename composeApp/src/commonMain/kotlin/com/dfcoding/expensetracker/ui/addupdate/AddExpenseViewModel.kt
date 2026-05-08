package com.dfcoding.expensetracker.ui.addupdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepositoryInterface
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.launch

class AddExpenseViewModel(
    private val repository: ExpenseRepositoryInterface,
) : ViewModel() {

    fun saveExpense(
        id: Long? = null,
        amount: Double,
        category: ExpenseCategory,
        description: String,
        date: Long,
        pocketId: Long
    ) {
        viewModelScope.launch {
            val expense = Expense(
                id = id ?: 0,
                amount = amount,
                category = category,
                description = description,
                date = date,
                pocketId = pocketId
            )

            if (id != null) {
                repository.updateExpense(expense)
            } else {
                repository.addExpense(expense)
            }




        }
    }
}