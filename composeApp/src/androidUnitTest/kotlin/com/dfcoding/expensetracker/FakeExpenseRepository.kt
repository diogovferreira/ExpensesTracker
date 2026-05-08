package com.dfcoding.expensetracker

import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.data.repository.ExpenseRepositoryInterface
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.domain.model.ExpenseWithPocket
import com.dfcoding.expensetracker.domain.model.Pocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeExpenseRepository : ExpenseRepositoryInterface {

    private val pockets = MutableStateFlow<List<Pocket>>(emptyList())
    private val expenses = MutableStateFlow<List<Expense>>(emptyList())

    // --- Pockets ---
    override fun getAllPockets(): Flow<List<Pocket>> = pockets
    override fun getPocketById(id: Long): Flow<Pocket?> =
        pockets.map { list -> list.find { it.id == id } }
    override suspend fun addPocket(pocket: Pocket) =
        pockets.update { it + pocket }
    override suspend fun updatePocket(pocket: Pocket) =
        pockets.update { list -> list.map { if (it.id == pocket.id) pocket else it } }
    override suspend fun deletePocket(id: Long) =
        pockets.update { list -> list.filter { it.id != id } }

    // --- Expenses ---
    override fun getAllExpenses(pocketId: Long): Flow<List<Expense>> =
        expenses.map { list -> list.filter { it.pocketId == pocketId } }
    override fun getExpenseById(id: Long): Flow<Expense?> =
        expenses.map { list -> list.find { it.id == id } }
    override suspend fun addExpense(expense: Expense) =
        expenses.update { it + expense }
    override suspend fun updateExpense(expense: Expense) =
        expenses.update { list -> list.map { if (it.id == expense.id) expense else it } }
    override suspend fun deleteExpense(id: Long) =
        expenses.update { list -> list.filter { it.id != id } }

    // --- Totals ---
    override fun getTotalAmount(): Flow<Double> =
        expenses.map { list -> list.sumOf { it.amount } }
    override fun getTotalByCategory(): Flow<Map<ExpenseCategory, Double>> =
        expenses.map { list -> list.groupBy { it.category }.mapValues { (_, v) -> v.sumOf { it.amount } } }
    override fun getTotalNumberOfExpenses(): Flow<Double> =
        expenses.map { it.size.toDouble() }
    override fun getExpenseWithPocket(): Flow<List<ExpenseWithPocket>> =
        expenses.map { emptyList() }

    // --- Test helpers ---
    fun setPockets(list: List<Pocket>) { pockets.value = list }
    fun setExpenses(list: List<Expense>) { expenses.value = list }
}