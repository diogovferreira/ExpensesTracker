package com.dfcoding.expensetracker.data.repository

import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.domain.model.ExpenseWithPocket
import com.dfcoding.expensetracker.domain.model.Pocket
import kotlinx.coroutines.flow.Flow

interface ExpenseRepositoryInterface {
    fun getAllPockets(): Flow<List<Pocket>>
    fun getPocketById(id: Long): Flow<Pocket?>
    suspend fun addPocket(pocket: Pocket)
    suspend fun updatePocket(pocket: Pocket)
    suspend fun deletePocket(id: Long)
    fun getAllExpenses(pocketId: Long): Flow<List<Expense>>
    fun getExpenseById(id: Long): Flow<Expense?>
    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(id: Long)
    fun getTotalAmount(): Flow<Double>
    fun getTotalByCategory(): Flow<Map<ExpenseCategory, Double>>
    fun getTotalNumberOfExpenses(): Flow<Double>
    fun getExpenseWithPocket(): Flow<List<ExpenseWithPocket>>
}