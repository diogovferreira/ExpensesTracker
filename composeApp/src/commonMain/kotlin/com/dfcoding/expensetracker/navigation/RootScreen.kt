package com.dfcoding.expensetracker.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.ui.addpocket.AddPocketScreen
import com.dfcoding.expensetracker.ui.expenses.ExpenseListScreen
import com.dfcoding.expensetracker.ui.history.HistoryScreen
import com.dfcoding.expensetracker.ui.home.HomeBottomBar
import com.dfcoding.expensetracker.ui.home.HomePocketScreen
import com.dfcoding.expensetracker.ui.home.HomePocketViewModel
import com.dfcoding.expensetracker.ui.settings.SettingsScreen
import com.dfcoding.expensetracker.ui.statistics.StatisticsScreen
import org.koin.compose.viewmodel.koinViewModel

class RootScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<HomePocketViewModel>()
        var currentTab by remember { mutableStateOf("home") }

        Scaffold(
            bottomBar = {
                HomeBottomBar(
                    currentRoute = currentTab,
                    onNavigateHome = { currentTab = "home" },
                    onNavigateStats = { currentTab = "stats" },
                    onNavigateAdd = { navigator.push(AddPocketScreen(null)) },
                    onNavigateHistory = { currentTab = "history" },
                    onNavigateProfile = { currentTab = "profile" }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(bottom = padding.calculateBottomPadding())) {
                when (currentTab) {
                    "home" -> HomePocketScreen(
                        onAddPocket = { navigator.push(AddPocketScreen(null)) },
                        onEditPocket = { id ->
                            val pocket = viewModel.getPocketById(id)
                            navigator.push(AddPocketScreen(pocket = pocket))
                        },
                        onDeletePocket = { viewModel.deletePocket(it) },
                        onPocketClick = { navigator.push(ExpenseListScreen(it)) }
                    ).Content()

                    "stats" -> StatisticsScreen().Content()
                    "history" -> HistoryScreen().Content()
                    "profile" -> SettingsScreen().Content()
                }
            }
        }
    }
}