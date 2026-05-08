package com.dfcoding.expensetracker.data.repository


import com.dfcoding.expensetracker.data.local.ExpenseDatabaseWrapper
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.domain.model.ExpenseWithPocket
import com.dfcoding.expensetracker.domain.model.Pocket
import kotlinx.coroutines.flow.Flow

open class ExpenseRepository(
    private val databaseWrapper: ExpenseDatabaseWrapper
) : ExpenseRepositoryInterface {

    override fun getAllPockets(): Flow<List<Pocket>> {
        return databaseWrapper.getAllPockets()
    }

    override fun getPocketById(id: Long): Flow<Pocket?> {
        return databaseWrapper.getPocketById(id)
    }

    override suspend fun addPocket(pocket: Pocket) {
        databaseWrapper.insertPocket(pocket)
    }

    override suspend fun updatePocket(pocket: Pocket) {
        databaseWrapper.updatePocket(pocket)
    }

    override suspend fun deletePocket(id: Long) {
        databaseWrapper.deletePocket(id)
    }

    override fun getAllExpenses(pocketId: Long): Flow<List<Expense>> {
        return databaseWrapper.getAllExpenses(pocketId)
    }

    override fun getExpenseById(id: Long): Flow<Expense?> {
        return databaseWrapper.getExpenseById(id)
    }

    override suspend fun addExpense(expense: Expense) {
        databaseWrapper.insertExpense(expense)
    }

    override suspend fun updateExpense(expense: Expense) {
        databaseWrapper.updateExpense(expense)
    }

    override suspend fun deleteExpense(id: Long) {
        databaseWrapper.deleteExpense(id)
    }

    override fun getTotalAmount(): Flow<Double> {
        return databaseWrapper.getTotalAmount()
    }

    override fun getTotalByCategory(): Flow<Map<ExpenseCategory, Double>> {
        return databaseWrapper.getTotalByCategory()
    }

    override fun getTotalNumberOfExpenses() : Flow<Double>{
        return databaseWrapper.getTotalNumber()

    }

    override fun getExpenseWithPocket(): Flow<List<ExpenseWithPocket>>{
        return databaseWrapper.getExpensesWithPocketInfo()
    }
}