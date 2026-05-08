package com.dfcoding.expensetracker

import com.dfcoding.expensetracker.data.local.ExpenseDatabaseWrapper
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.database.ExpenseDatabase
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ExpenseRepositoryTest {
    private lateinit var repository: ExpenseRepository

    @BeforeTest
    fun setup() {
        val driver = createTestDriver() //create driver for db
        val database = ExpenseDatabase(driver) //create database
        val wrapper = ExpenseDatabaseWrapper(database) //create wrapper
        repository = ExpenseRepository(wrapper) //create repo
    }

    @Test
    fun `insert and get pocket`() = runTest {
        val expense = Expense(
            id = 0L,
            amount = 12.50,
            description = "Coffee",
            category = ExpenseCategory.FOOD,
            date = Clock.System.now().toEpochMilliseconds(),
            pocketId = 1L
        )

        repository.addExpense(expense)

        val result = repository.getAllExpenses(pocketId = 1L).first()
        assertEquals(1, result.size)
        assertEquals("Coffee", result[0].description)
    }

    //Delete expense
    @Test
    fun `add and delete expense`() = runTest {
        val expense = Expense(
            id = 0L,
            amount = 12.50,
            description = "Coffee",
            category = ExpenseCategory.FOOD,
            date = Clock.System.now().toEpochMilliseconds(),
            pocketId = 1L
        )

        //add expense first
        repository.addExpense(expense)

        val inserted = repository.getAllExpenses(pocketId = 1L).first()
        assertEquals(1, inserted.size)

        //delete expense
        repository.deleteExpense(inserted[0].id)
        val afterDelete = repository.getAllExpenses(pocketId = 1L).first()
        assertEquals(0, afterDelete.size)
    }

    @Test
    fun `expenses are filtered by pocket`() = runTest {
        val expense1 = Expense(
            id = 0L, amount = 12.50, description = "Coffee",
            category = ExpenseCategory.FOOD,
            date = Clock.System.now().toEpochMilliseconds(),
            pocketId = 1L  // pocket 1
        )
        val expense2 = Expense(
            id = 0L, amount = 5.0, description = "Bus",
            category = ExpenseCategory.TRANSPORT,
            date = Clock.System.now().toEpochMilliseconds(),
            pocketId = 2L  // pocket 2
        )

        repository.addExpense(expense1)
        repository.addExpense(expense2)

        val result = repository.getAllExpenses(pocketId = 1L).first()
        assertEquals(1, result.size)
        assertEquals("Coffee", result[0].description)
    }

    //Update Expenses

    @Test
    fun `update expense`() = runTest {
        val expense = Expense(
            id = 0L,
            amount = 12.50,
            description = "Coffee",
            category = ExpenseCategory.FOOD,
            date = Clock.System.now().toEpochMilliseconds(),
            pocketId = 1L
        )

        repository.addExpense(expense)

        val inserted = repository.getAllExpenses(pocketId = 1L).first()
        assertEquals("Coffee", inserted[0].description)

        val expenseUpdated = Expense(
            id = inserted[0].id,
            amount = inserted[0].amount,
            description = "Soda",
            category = inserted[0].category,
            date = Clock.System.now().toEpochMilliseconds(),
            pocketId = 1L
        )

        repository.updateExpense(expenseUpdated)

        val updated = repository.getAllExpenses(pocketId = 1L).first()
        assertEquals("Soda", updated[0].description)

    }

}

