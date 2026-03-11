package com.dfcoding.expensetracker.domain.model

data class Expense(
    val id: Long = 0,  // Auto-generated
    val amount: Double,
    val category: ExpenseCategory,
    val description: String = "",
    val date: Long,  // Timestamp in milliseconds
    val pocketId: Long? = null
)
