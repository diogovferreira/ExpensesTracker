package com.dfcoding.expensetracker.ui.addpocket

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.domain.model.Pocket
import com.dfcoding.expensetracker.domain.model.PocketIcon
import com.dfcoding.expensetracker.ui.components.IconPickerBottomSheet
import com.dfcoding.expensetracker.ui.components.LongButton
import com.dfcoding.expensetracker.ui.components.ScreenHeader
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

class AddPocketScreen(val pocket: Pocket? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<AddPocketViewModel>()

        AddPocketContent(
            goBack = { navigator.pop() },
            onSavePocketButtonClick = { name, currency, icon ->
                if (pocket == null) {
                    viewModel.addPocket(name = name, icon = icon, currency = currency)
                } else {
                    viewModel.updatePocket(
                        id = pocket.id,
                        name = name,
                        icon = icon,
                        currency = currency
                    )
                }
                navigator.pop()
            },
            pocket = pocket
        )
    }

}

@Preview
@Composable
fun AddPocketContent(
    pocket: Pocket? = null,
    goBack: () -> Unit = {},
    onSavePocketButtonClick: (String, String, String) -> Unit = { _, _, _ -> }
) {

    val isEditing = pocket != null

    var pocketName by remember { mutableStateOf(pocket?.name ?: "") }
    var selectedCurrency by remember { mutableStateOf(pocket?.currency ?: "EUR") }
    var selectedIcon by remember {
        mutableStateOf(PocketIcon.entries.find { it.emoji == pocket?.icon } ?: PocketIcon.VACATION)
    }
    var showIconPicker by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.White))

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).statusBarsPadding()
    ) {

        // Header
        ScreenHeader(
            title = if (isEditing) "Edit Pocket" else "New Pocket",
            onBackClick = goBack
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
                text = "Pocket Details",
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
                    Text(text = selectedIcon.emoji, fontSize = 20.sp)
                }

                if (showIconPicker) {
                    IconPickerBottomSheet(
                        icons = PocketIcon.entries,
                        selectedIcon = selectedIcon,
                        onIconSelected = { selectedIcon = it },
                        onDismiss = { showIconPicker = false }
                    )
                }
                TextField(
                    value = pocketName,
                    onValueChange = { pocketName = it },
                    placeholder = { Text("Pocket name...") },
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

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Currency",
                style = MaterialTheme.typography.labelLarge,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold
            )
            CurrencyDropDown(
                selectedCurrency = selectedCurrency,
                onCurrencySelected = { selectedCurrency = it }
            )
        }

        // This spacer pushes the button to the bottom
        Spacer(modifier = Modifier.weight(1f))

        LongButton(
            modifier = Modifier.navigationBarsPadding(),
            text = if (isEditing) "Update Pocket" else "Save Pocket",
            onButtonClick = {
                onSavePocketButtonClick(
                    pocketName,
                    selectedCurrency,
                    selectedIcon.emoji
                )
            },
            isEnabled = pocketName.isNotEmpty()
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropDown(
    selectedCurrency: String = "EUR",
    onCurrencySelected: (String) -> Unit = {}
) {

    var expanded by remember { mutableStateOf(false) }

    val currencies = listOf("EUR")


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedCurrency,
            onValueChange = {},
            readOnly = true, // user can't type, only select
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .menuAnchor() // this links the TextField to the dropdown
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

