package com.dfcoding.expensetracker.ui.addupdate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.ui.components.ScreenHeader
import com.dfcoding.expensetracker.ui.screens.addedit.AddExpenseViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


data class AddExpenseScreen(
    val expenseId: Long? = null
) : Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<AddExpenseViewModel>(
            parameters = { parametersOf(expenseId) }
        )

        AddExpenseContent(
            viewModel = viewModel,
            onNavigateBack = { navigator.pop() }
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddExpenseContent(
    viewModel: AddExpenseViewModel,
    onNavigateBack: () -> Unit
) {

    AddExpenseListContentStateless(
        onNavigateBack = onNavigateBack,
        onSaveExpense = { amount, category, description ->
            viewModel.saveExpense(
                amount = amount,
                category = category,
                description = description
            )
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseListContentStateless(
    onNavigateBack: () -> Unit,
    onSaveExpense: (Double, ExpenseCategory, String) -> Unit
) {

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ExpenseCategory.FOOD) }

    Surface(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars)) {
        Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {

                ScreenHeader(title = "Add Expense", onBackClick = onNavigateBack)
                // Amount field
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    leadingIcon = { Text("$") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Category selector
                Text(
                    "Category",
                    style = MaterialTheme.typography.labelLarge
                )

                CategorySelector(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                // Description field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.weight(1f))

                // Save button
                Button(
                    onClick = {
                        val amountValue = amount.toDoubleOrNull()
                        if (amountValue != null && amountValue > 0) {
                            onSaveExpense(amountValue, selectedCategory, description)
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = amount.toDoubleOrNull()?.let { it > 0 } ?: false
                ) {
                    Text("Save Expense")
                }

        }
    }
}

@Composable
fun CategorySelector(
    selectedCategory: ExpenseCategory,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ExpenseCategory.entries.chunked(4).forEach { rowCategories ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowCategories.forEach { category ->
                    FilterChip(
                        selected = category == selectedCategory,
                        onClick = { onCategorySelected(category) },
                        label = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(category.icon)
                                Text(category.displayName)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                // Fill empty spaces
                repeat(4 - rowCategories.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
@Preview
fun AddExpenseScreenPreview(){
    MaterialTheme {
        AddExpenseListContentStateless(
            onNavigateBack = {},
            onSaveExpense = { _, _, _ ->})
    }
}