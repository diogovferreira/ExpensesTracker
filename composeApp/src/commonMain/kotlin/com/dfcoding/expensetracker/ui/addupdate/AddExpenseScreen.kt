package com.dfcoding.expensetracker.ui.addupdate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.domain.model.Expense
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.ui.components.IconPickerBottomSheet
import com.dfcoding.expensetracker.ui.components.LongButton
import com.dfcoding.expensetracker.ui.components.ScreenHeader
import com.dfcoding.expensetracker.util.Formatter
import kotlinx.datetime.Clock
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.exp


data class AddExpenseScreen(
    val expense: Expense? = null
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<AddExpenseViewModel>()

        AddExpenseContent(
            onNavigateBack = { navigator.pop() },
            onSaveExpense = { amount, category, description, date ->
                if (expense != null) {
                    viewModel.saveExpense(
                        id = expense.id,
                        amount = amount,
                        category = category,
                        description = description,
                        date = date

                    )
                } else {
                    viewModel.saveExpense(
                        amount = amount,
                        category = category,
                        description = description,
                        date = date
                    )
                }
            },
            expense
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddExpenseContent(
    onNavigateBack: () -> Unit,
    onSaveExpense: (Double, ExpenseCategory, String, Long) -> Unit,
    expense: Expense? = null
) {


    AddExpenseListContentStateless(
        onNavigateBack = onNavigateBack,
        onSaveExpense = { amount, category, description, date ->
            onSaveExpense(
                amount,
                category,
                description,
                date
            )
        },
        expense
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseListContentStateless(
    onNavigateBack: () -> Unit,
    onSaveExpense: (Double, ExpenseCategory, String, Long) -> Unit,
    expense: Expense? = null
) {

    val isEditing = expense != null

    var amount by remember { mutableStateOf(expense?.amount?.toString() ?: "") }
    var description by remember { mutableStateOf(expense?.description ?: "") }
    var selectedCategory by remember {
        mutableStateOf(ExpenseCategory.entries.find { it.emoji == expense?.category?.emoji }
            ?: ExpenseCategory.FOOD)
    }

    var selectedDate by remember {
        mutableStateOf(
            expense?.date ?: Clock.System.now().toEpochMilliseconds()
        )
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White))

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).statusBarsPadding()) {

        ScreenHeader(
            title = if (isEditing) "Edit Expense" else "Add Expense",
            onBackClick = onNavigateBack
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Amount",
                style = MaterialTheme.typography.labelLarge,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { showIconPicker = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = selectedCategory.emoji, fontSize = 20.sp)
                }

                if (showIconPicker) {
                    IconPickerBottomSheet(
                        icons = ExpenseCategory.entries,
                        selectedIcon = selectedCategory,
                        onIconSelected = { selectedCategory = it },
                        onDismiss = { showIconPicker = false }
                    )
                }
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    placeholder = { Text("Amount") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .clickable { showDatePicker = true }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Formatter.formatDate(selectedDate),
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily.Monospace,
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Pick date",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (showDatePicker) {
                ExpenseDatePicker(
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it },
                    onDismiss = { showDatePicker = false }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Spacer(modifier = Modifier.height(14.dp))

            // Description field
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Description",
                style = MaterialTheme.typography.labelLarge,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
            )
        }


        Spacer(modifier = Modifier.weight(1f))

        // Save button
        LongButton(
            modifier = Modifier.navigationBarsPadding(),
            text = if (isEditing) "Update Expense" else "Save Expense",
            onButtonClick = {
                val amountValue = amount.toDoubleOrNull()
                if (amountValue != null && amountValue > 0) {
                    onSaveExpense(amountValue, selectedCategory, description, selectedDate)
                    onNavigateBack()
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePicker(
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
@Preview
fun AddExpenseScreenPreview() {
    MaterialTheme {
        AddExpenseListContentStateless(
            onNavigateBack = {},
            onSaveExpense = { _, _, _, _ -> })
    }
}