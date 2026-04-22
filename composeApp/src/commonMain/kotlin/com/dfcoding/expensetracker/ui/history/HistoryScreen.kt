package com.dfcoding.expensetracker.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.dfcoding.expensetracker.domain.model.ExpenseWithPocket
import com.dfcoding.expensetracker.ui.expenses.ExpensePocketListItem
import kotlinx.datetime.Clock
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

class HistoryScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<HistoryScreenViewModel>()

        val expensesWithPocketInfo by viewModel.groupedExpenses.collectAsState()

        HistoryContent(expensesWithPocketInfo)
    }
}

@Composable
fun HistoryContent(expensesWithPocketInfo: List<ExpensePocketListItem>) {
    HistoryContentStateless(expensesWithPocketInfo)
}

@Composable
fun HistoryContentStateless(expensesWithPocketInfo: List<ExpensePocketListItem>) {
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

    }
    Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {

        Text(
            modifier = Modifier.padding(16.dp),
            text = "All Expenses",
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Global timeline across all pockets",
            style = MaterialTheme.typography.labelSmall,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal,
            color = Color.White
        )

        //Stats Card
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "Total Spent",
                        style = MaterialTheme.typography.labelLarge,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                    Text(
                        text = expensesWithPocketInfo.filter { item -> item is ExpensePocketListItem.ExpensePocketItem }
                            .sumOf { (it as ExpensePocketListItem.ExpensePocketItem).expensePocket.amount }
                            .toString() + "€",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }
                VerticalDivider(modifier = Modifier.height(50.dp), color = Color.White.copy(0.2f))

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "Entries",
                        style = MaterialTheme.typography.labelLarge,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                    Text(
                        text = expensesWithPocketInfo.filter { item -> item is ExpensePocketListItem.ExpensePocketItem }.size.toString(),
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(25.dp))

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

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(expensesWithPocketInfo) { item ->
                        when (item) {
                            is ExpensePocketListItem.DateHeader -> {
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

                            is ExpensePocketListItem.ExpensePocketItem -> {
                                ExpenseItem(item.expensePocket)
                            }
                        }

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExpenseItem(
    expensePocket: ExpenseWithPocket
) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentSize().background(Color.White),
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
                    text = expensePocket.category.emoji,
                    style = MaterialTheme.typography.headlineMedium
                )

                // Details column
                Column {
                    Text(
                        text = expensePocket.description,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Monospace
                    )
                    Row() {
                        Text(
                            text = expensePocket.pocketIcon,
                            style = MaterialTheme.typography.labelLarge
                        )

                        Text(
                            text = expensePocket.pocketName,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily.Monospace
                        )
                    }


                }
            }
            Text(
                text = "${expensePocket.amount}€",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
@Preview
fun HistoryContentStatelessPreview() {
    MaterialTheme {
        HistoryContentStateless(
            expensesWithPocketInfo = listOf(
                ExpensePocketListItem.DateHeader("Today"),
                ExpensePocketListItem.ExpensePocketItem(
                    ExpenseWithPocket(
                        id = 1,
                        amount = 50.0,
                        category = ExpenseCategory.FOOD,
                        description = "Lunch",
                        date = Clock.System.now().toEpochMilliseconds(),
                        pocketName = "TestePocket",
                        pocketIcon = "💰"
                    )
                )
            )
        )
    }
}