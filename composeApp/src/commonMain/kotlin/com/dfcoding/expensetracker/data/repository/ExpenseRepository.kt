package com.dfcoding.expensetracker.data.repository


import coil3.annotation.Poko
import com.dfcoding.expensetracker.data.local.ExpenseDatabaseWrapper
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.domain.model.ExpenseWithPocket
import com.dfcoding.expensetracker.domain.model.Pocket
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val databaseWrapper: ExpenseDatabaseWrapper
) {

    fun getAllPockets(): Flow<List<Pocket>> {
        return databaseWrapper.getAllPockets()
    }

    fun getPocketById(id: Long): Flow<Pocket?> {
        return databaseWrapper.getPocketById(id)
    }

    suspend fun addPocket(pocket: Pocket) {
        databaseWrapper.insertPocket(pocket)
    }

    suspend fun updatePocket(pocket: Pocket) {
        databaseWrapper.updatePocket(pocket)
    }

    suspend fun deletePocket(id: Long) {
        databaseWrapper.deletePocket(id)
    }

    fun getAllExpenses(pocketId: Long): Flow<List<Expense>> {
        return databaseWrapper.getAllExpenses(pocketId)
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

    fun getTotalNumberOfExpenses() : Flow<Double>{
        return databaseWrapper.getTotalNumber()

    }

    fun getExpenseWithPocket(): Flow<List<ExpenseWithPocket>>{
        return databaseWrapper.getExpensesWithPocketInfo()
    }
}