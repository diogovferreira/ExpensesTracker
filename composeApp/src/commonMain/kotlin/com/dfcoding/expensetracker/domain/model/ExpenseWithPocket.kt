package com.dfcoding.expensetracker.domain.model

data class ExpenseWithPocket(
    val id: Long,
    val description: String,
    val amount: Double,
    val date: Long,
    val category: ExpenseCategory,
    val pocketName: String,
    val pocketIcon: String
)
