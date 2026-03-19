package com.dfcoding.expensetracker.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.domain.model.Pocket
import com.dfcoding.expensetracker.ui.addupdate.AddExpenseScreen
import com.dfcoding.expensetracker.ui.components.ScreenHeader
import com.dfcoding.expensetracker.util.Formatter.formatDate
import org.koin.compose.viewmodel.koinViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class ExpenseListScreen(val pocket: Pocket) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<ExpenseListViewModel>()

        ExpenseListContent(
            viewModel = viewModel,
            onAddExpense = { navigator.push(AddExpenseScreen()) },
            onEditExpense = { expenseId -> navigator.push(AddExpenseScreen(expenseId)) },
            onDeleteExpense = { id -> viewModel.deleteExpense(id) },
            onNavigateBack = { navigator.pop() }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ExpenseListContent(
    viewModel: ExpenseListViewModel = koinViewModel(),
    onAddExpense: () -> Unit = {},
    onEditExpense: (Long) -> Unit = {},
    onDeleteExpense: (Long) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val expenses by viewModel.expenses.collectAsState()

    ExpenseListContentStateless(
        expenses = expenses,
        onAddExpense = onAddExpense,
        onEditExpense = onEditExpense,
        onDeleteExpense = onDeleteExpense,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListContentStateless(
    expenses: List<Expense>,
    onAddExpense: () -> Unit,
    onEditExpense: (Long) -> Unit,
    onDeleteExpense: (Long) -> Unit,
    onNavigateBack: () -> Unit,
) {


    Scaffold(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars),
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                FloatingActionButton(
                    onClick = { onAddExpense() },
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
                }
            }
        }
    ) { padding ->

        Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {

            // Header
            ScreenHeader(
                title = "Expense List",
                onBackClick = onNavigateBack
            )

            if (expenses.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "No expenses yet! 💰",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            "Tap the + button to add test expenses",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                // List of expenses
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    // Total amount card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "Total Expenses",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                "$${expenses.sumOf { it.amount }}",
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                "${expenses.size} expense${if (expenses.size != 1) "s" else ""}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    // Expense list
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = expenses,
                            key = { it.id }
                        ) { expense ->
                            ExpenseItem(
                                expense = expense,
                                onDelete = { onDeleteExpense(expense.id) }
                            )
                        }
                    }
                }
            }
        }


    }
}


@Composable
fun ExpenseItem(
    expense: Expense,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon and details
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category icon
                Text(
                    text = expense.category.emoji,
                    style = MaterialTheme.typography.headlineMedium
                )

                // Details column
                Column {
                    Text(
                        text = expense.category.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (expense.description.isNotEmpty()) {
                        Text(
                            text = expense.description,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = formatDate(expense.date),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Amount and delete button
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "$${expense.amount}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}


@OptIn(ExperimentalTime::class)
@Preview
@Composable
fun ExpenseListPreview() {
    MaterialTheme {
        ExpenseListContentStateless(
            expenses = listOf(
                Expense(
                    id = 1,
                    amount = 50.0,
                    category = ExpenseCategory.FOOD,
                    description = "Lunch",
                    date = Clock.System.now().toEpochMilliseconds()
                ),
                Expense(
                    id = 2,
                    amount = 120.0,
                    category = ExpenseCategory.TRANSPORT,
                    description = "Uber",
                    date = Clock.System.now().toEpochMilliseconds()
                )
            ),
            onAddExpense = {},
            onEditExpense = {},
            onDeleteExpense = {},
            onNavigateBack = {}
        )
    }
}
