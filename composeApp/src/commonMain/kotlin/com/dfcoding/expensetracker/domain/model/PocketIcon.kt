package com.dfcoding.expensetracker.domain.model

enum class PocketIcon(override val emoji: String, override val displayName: String) :
    SelectableIcon {
    TRAVEL("✈️", "Travel"),
    VACATION("🏖️", "Vacation"),
    SHOPPING("🛍️", "Shopping"),
    FOOD("🍔", "Food Trip"),
    ENTERTAINMENT("🎬", "Entertainment"),
    GIFT("🎁", "Gift"),
    PROJECT("📊", "Project"),
    EVENT("🎉", "Event"),
    HEALTH("🏥", "Health"),
    OTHER("📌", "Other")

}

