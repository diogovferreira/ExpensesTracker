package com.dfcoding.expensetracker.domain.model

enum class ExpenseCategory(val displayName: String, val icon: String) {
    FOOD("Food", "🍔"),
    TRANSPORT("Transport", "🚗"),
    SHOPPING("Shopping", "🛍️"),
    ENTERTAINMENT("Entertainment", "🎬"),
    BILLS("Bills", "💡"),
    HEALTH("Health", "🏥"),
    OTHER("Other", "📌")
}
