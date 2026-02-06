package com.dfcoding.expensetracker

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.dfcoding.expensetracker.ui.list.ExpenseListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        ExpenseListScreen()
    }
}