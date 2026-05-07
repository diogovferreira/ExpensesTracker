package com.dfcoding.expensetracker

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.dfcoding.expensetracker.database.ExpenseDatabase

actual fun createTestDriver(): SqlDriver {
    return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { driver ->
        ExpenseDatabase.Schema.create(driver)
    }
}