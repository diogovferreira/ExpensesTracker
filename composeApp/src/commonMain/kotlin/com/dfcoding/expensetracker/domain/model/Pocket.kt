package com.dfcoding.expensetracker.domain.model

data class Pocket(
    val id: Long = 0, // Auto-Generated
    val name: String = "",
    val icon: String,
    val date: Long,
    val currency: String
)

data class PocketTotal(
    val pocket: Pocket,
    val totalAmount: Double,
    val expensesCount: Int
)


