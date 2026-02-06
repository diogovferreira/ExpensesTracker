package com.dfcoding.expensetracker.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = ExpenseDatabase.Schema,
            name = "expense.db"
        )
    }
}