package com.dfcoding.expensetracker.data.repository


import com.dfcoding.expensetracker.data.local.ExpenseDatabaseWrapper
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val databaseWrapper: ExpenseDatabaseWrapper
) {
    fun getAllExpenses(): Flow<List<Expense>> {
        return databaseWrapper.getAllExpenses()
    }

    fun getExpenseById(id: Long): Flow<Expense?> {
        return databaseWrapper.getExpenseById(id)
    }

    suspend fun addExpense(expense: Expense) {
        databaseWrapper.insertExpense(expense)
    }

    suspend fun updateExpense(expense: Expense) {
        databaseWrapper.updateExpense(expense)
    }

    suspend fun deleteExpense(id: Long) {
        databaseWrapper.deleteExpense(id)
    }

    fun getTotalAmount(): Flow<Double> {
        return databaseWrapper.getTotalAmount()
    }

    fun getTotalByCategory(): Flow<Map<ExpenseCategory, Double>> {
        return databaseWrapper.getTotalByCategory()
    }
}