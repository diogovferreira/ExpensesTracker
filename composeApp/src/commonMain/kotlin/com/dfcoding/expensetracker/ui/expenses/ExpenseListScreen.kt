package com.dfcoding.expensetracker.ui.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.domain.model.Pocket
import com.dfcoding.expensetracker.ui.addupdate.AddExpenseScreen
import com.dfcoding.expensetracker.ui.components.LongButton
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class ExpenseListScreen(val pocket: Pocket) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<ExpenseListViewModel>()

        ExpenseListContent(
            viewModel = viewModel,
            pocket = pocket,
            onAddExpense = { navigator.push(AddExpenseScreen()) },
            onEditExpense = { expense -> navigator.push(AddExpenseScreen(expense)) },
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
    pocket: Pocket,
    onAddExpense: () -> Unit = {},
    onEditExpense: (Expense) -> Unit = {},
    onDeleteExpense: (Long) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val groupedExpenses by viewModel.groupedExpenses.collectAsState()

    ExpenseListContentStateless(
        groupedExpenses = groupedExpenses,
        pocket = pocket,
        onAddExpense = onAddExpense,
        onEditExpense = onEditExpense,
        onDeleteExpense = onDeleteExpense,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListContentStateless(
    groupedExpenses: List<ExpenseListItem>,
    pocket: Pocket,
    onAddExpense: () -> Unit,
    onEditExpense: (Expense) -> Unit,
    onDeleteExpense: (Long) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val purpleGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f) // fades out at bottom
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        //PURPLE PART
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f)
                .background(purpleGradient)
        )

        Box(modifier = Modifier.fillMaxSize())
    }
    Column(modifier = Modifier.fillMaxSize()) {

        // Header
        ExpenseListHeader(
            onBackClick = onNavigateBack,
            groupedExpenses.filter { item -> item is ExpenseListItem.ExpenseEntry }.size.toString(),
            pocket = pocket
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Total Expenses",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
                Text(
                    text = groupedExpenses.filter { item -> item is ExpenseListItem.ExpenseEntry }
                        .sumOf { (it as ExpenseListItem.ExpenseEntry).expense.amount }
                        .toString() + "€",
                    style = MaterialTheme.typography.displaySmall,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = groupedExpenses.filter { item -> item is ExpenseListItem.ExpenseEntry }
                        .size.toString() + " Expenses",
                    style = MaterialTheme.typography.titleSmall,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }



        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize().navigationBarsPadding()
            ) {
                // Expense list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = groupedExpenses
                    ) { item ->
                        when (item) {
                            is ExpenseListItem.DateHeader -> {
                                Text(
                                    text = item.date.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        top = 16.dp,
                                        bottom = 4.dp
                                    )
                                )
                            }

                            is ExpenseListItem.ExpenseEntry -> {
                                ExpenseItem(
                                    expense = item.expense,
                                    onDelete = { onDeleteExpense(item.expense.id) },
                                    onEdit = { onEditExpense(item.expense) }

                                )

                            }
                        }
                    }
                }

                LongButton("Add Expense", onButtonClick = onAddExpense, isEnabled = true)
            }
        }
    }
}


@Composable
fun ExpenseItem(
    expense: Expense,
    onDelete: (Long) -> Unit,
    onEdit: (Expense) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentSize().clickable{ onEdit(expense) },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(0.2f))
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
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Monospace
                    )
                    if (expense.description.isNotEmpty()) {
                        Text(
                            text = expense.description,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.spacedBy(10.dp)){
                Text(
                    text = "${expense.amount}€",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )
               Icon(
                   modifier = Modifier.clickable{ onDelete(expense.id) },
                   imageVector = Icons.Default.Delete,
                   contentDescription = "Delete",
                   tint = Color.Red,
               )

            }


        }
    }
}

@Composable
@Preview
fun ExpenseListHeader(onBackClick: () -> Unit, numberOfExpenses: String, pocket: Pocket) {

    Row(modifier = Modifier.fillMaxWidth().padding(16.dp).statusBarsPadding()) {
        Box(
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { onBackClick() }.padding(10.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = pocket.icon, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = pocket.name,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                text = "$numberOfExpenses Expenses",
                style = MaterialTheme.typography.titleSmall,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }


    }

}



@OptIn(ExperimentalTime::class)
@Preview
@Composable
fun ExpenseListPreview() {
    MaterialTheme {
        ExpenseListContentStateless(
            groupedExpenses = listOf(
                ExpenseListItem.DateHeader("Today"),
                ExpenseListItem.ExpenseEntry(
                    Expense(
                        id = 1,
                        amount = 50.0,
                        category = ExpenseCategory.FOOD,
                        description = "Lunch",
                        date = Clock.System.now().toEpochMilliseconds()
                    )

                )
            ),
            pocket = Pocket(
                id = 1,
                name = "Pocket 1",
                icon = "💰",
                date = 82828282828,
                currency = "",
        ),
        onAddExpense = {},
        onEditExpense = {},
        onDeleteExpense = {},
        onNavigateBack = {}
        )
    }
}
