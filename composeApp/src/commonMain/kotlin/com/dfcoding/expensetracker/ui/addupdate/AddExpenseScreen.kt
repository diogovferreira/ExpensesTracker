package com.dfcoding.expensetracker.ui.addupdate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.domain.model.ExpenseCategory
import com.dfcoding.expensetracker.ui.components.IconPickerBottomSheet
import com.dfcoding.expensetracker.ui.components.LongButton
import com.dfcoding.expensetracker.ui.components.ScreenHeader
import com.dfcoding.expensetracker.ui.screens.addedit.AddExpenseViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


data class AddExpenseScreen(
    val expenseId: Long? = null
) : Screen {
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

    var showIconPicker by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars)) {
        Column(modifier = Modifier.fillMaxSize().padding(32.dp)) {

            ScreenHeader(title = "Add Expense", onBackClick = onNavigateBack)

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(12.dp)
            ) {
                Text("Amount", style = MaterialTheme.typography.labelLarge)
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

                Spacer(modifier = Modifier.height(24.dp))

                // Description field
                Text("Description", style = MaterialTheme.typography.labelLarge)

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
                text = "Save Expense",
                onButtonClick = {
                    val amountValue = amount.toDoubleOrNull()
                    if (amountValue != null && amountValue > 0) {
                        onSaveExpense(amountValue, selectedCategory, description)
                        onNavigateBack()
                    }
                }
            )
        }
    }
}

@Composable
@Preview
fun AddExpenseScreenPreview() {
    MaterialTheme {
        AddExpenseListContentStateless(
            onNavigateBack = {},
            onSaveExpense = { _, _, _ -> })
    }
}