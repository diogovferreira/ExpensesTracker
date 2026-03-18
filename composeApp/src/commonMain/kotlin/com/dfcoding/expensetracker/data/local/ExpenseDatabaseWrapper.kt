package com.dfcoding.expensetracker.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.dfcoding.expensetracker.database.ExpenseDatabase
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.domain.model.Pocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseDatabaseWrapper(
    private val database: ExpenseDatabase
) {
    private val queries = database.expenseQueries

    // Get all pockets as Flow
    fun getAllPockets(): Flow<List<Pocket>>{
        return queries.getAllPockets().asFlow().mapToList(Dispatchers.Default).map { pocketEntities ->
            pocketEntities.map { it.toPocket() }
         }
    }

    fun getPocketById(id: Long): Flow<Pocket?> {
        return queries.getPocketById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toPocket() }
    }

    fun insertPocket(pocket: Pocket) {
        queries.insertPocket(
            name = pocket.name,
            icon = pocket.icon,
            date = pocket.date,
            currency = pocket.currency
        )
    }

    fun updatePocket(pocket: Pocket) {
        queries.updatePocket(
            id = pocket.id,
            name = pocket.name,
            icon = pocket.icon,
            date = pocket.date,
            currency = pocket.currency
        )
    }

    fun deletePocket(id: Long) {
        queries.deletePocket(id)
    }

    // Get all expenses as Flow
    fun getAllExpenses(): Flow<List<Expense>> {
        return queries.getAllExpenses()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { expenseEntities ->
                expenseEntities.map { it.toExpense() }
            }
    }

    // Get expense by ID
    fun getExpenseById(id: Long): Flow<Expense?> {
        return queries.getExpenseById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toExpense() }
    }

    // Insert expense
    fun insertExpense(expense: Expense) {
        queries.insertExpense(
            amount = expense.amount,
            category = expense.category.name,
            description = expense.description,
            date = expense.date,
            pocketId = expense.pocketId
        )
    }

    // Update expense
    fun updateExpense(expense: Expense) {
        queries.updateExpense(
            amount = expense.amount,
            category = expense.category.name,
            description = expense.description,
            date = expense.date,
            id = expense.id,
            pocketId = expense.pocketId
        )
    }

    // Delete expense
    fun deleteExpense(id: Long) {
        queries.deleteExpense(id)
    }

    // Get total amount
    fun getTotalAmount(): Flow<Double> {
        return queries.getTotalAmount()
            .asFlow()
            .mapToOne(Dispatchers.Default)
            .map { (it ?: 0.0) as Double }
    }

    // Get totals by category
    fun getTotalByCategory(): Flow<Map<ExpenseCategory, Double>> {
        return queries.getTotalByCategory()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { results ->
                results.associate {
                    ExpenseCategory.valueOf(it.category) to (it.total ?: 0.0)
                }
            }
    }

    // Helper extension to convert DB entity to domain model
    private fun com.dfcoding.expensetracker.database.Expense.toExpense() = Expense(
        id = id,
        amount = amount,
        category = ExpenseCategory.valueOf(category),
        description = description,
        date = date,
        pocketId = pocketId
    )

    private fun com.dfcoding.expensetracker.database.Pocket.toPocket() = Pocket(
        id = id,
        name = name,
        icon = icon,
        date = date,
        currency = currency
    )
}