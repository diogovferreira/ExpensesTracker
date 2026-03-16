package com.dfcoding.expensetracker

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.dfcoding.expensetracker.ui.list.ExpenseListContent
import com.dfcoding.expensetracker.ui.list.ExpenseListScreen
import com.dfcoding.expensetracker.ui.pockets.PocketScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(PocketScreen())
    }
}