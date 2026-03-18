package com.dfcoding.expensetracker.domain.model

enum class ExpenseCategory(override val displayName: String, override val emoji: String) : SelectableIcon {
    FOOD("Food", "🍔"),
    TRANSPORT("Transport", "🚗"),
    SHOPPING("Shopping", "🛍️"),
    ENTERTAINMENT("Entertainment", "🎬"),
    BILLS("Bills", "💡"),
    HEALTH("Health", "🏥"),
    OTHER("Other", "📌")
}
