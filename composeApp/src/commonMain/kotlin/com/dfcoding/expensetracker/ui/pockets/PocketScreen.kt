package com.dfcoding.expensetracker.ui.pockets

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.domain.model.Pocket
import com.dfcoding.expensetracker.ui.addpocket.AddPocketScreen
import com.dfcoding.expensetracker.ui.list.ExpenseListScreen
import com.dfcoding.expensetracker.util.Formatter
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

class PocketScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<PocketListViewModel>()

        PocketListContent(
            viewModel,
            onAddPocket = { navigator.push(AddPocketScreen(null)) },
            onEditPocket = { id ->
                val pocketToEdit = viewModel.getPocketById(id)
                navigator.push(AddPocketScreen(pocket = pocketToEdit))
            },
            onDeletePocket = { id ->
                viewModel.deletePocket(id)
            },
            onPocketClick = { pocket ->
                navigator.push(ExpenseListScreen(pocket))
            })
    }
}

@Preview
@Composable
fun PocketListContent(
    viewModel: PocketListViewModel = koinViewModel(),
    onAddPocket: () -> Unit = {},
    onEditPocket: (Long) -> Unit = {},
    onDeletePocket: (Long) -> Unit = {},
    onPocketClick: (Pocket) -> Unit = {}
) {

    val pockets by viewModel.pockets.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background).padding(32.dp)
    ) {
        //Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "My Pockets",
                modifier = Modifier.weight(1f), // takes remaining space, leaves room for FAB
                style = MaterialTheme.typography.headlineLarge
            )
            FloatingActionButton(onClick = onAddPocket) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pocket")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(pockets) { pocket ->
                PocketItem(
                    pocket = pocket,
                    onDeletePocket = onDeletePocket,
                    onEditPocket = onEditPocket,
                    onPocketClick = onPocketClick
                )
            }
        }
    }
}

@Composable
fun PocketItem(pocket: Pocket, onDeletePocket: (Long) -> Unit, onEditPocket: (Long) -> Unit, onPocketClick: (Pocket) -> Unit) {

    var showMenu by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth().height(100.dp).clickable { onPocketClick(pocket) }) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFFFF0F0)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = pocket.icon, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight().width(150.dp),
                    verticalArrangement = Arrangement.Center

                ) {
                    Text(text = pocket.name, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = Formatter.formatDate(pocket.date),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(text = "190 euros", style = MaterialTheme.typography.bodyLarge)

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            modifier = Modifier.wrapContentSize(),
                            contentDescription = "3 dots"
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit"
                                )
                            },
                            onClick = {
                                showMenu = false
                                onEditPocket(pocket.id)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            },
                            onClick = {
                                showMenu = false
                                onDeletePocket(pocket.id)
                            }
                        )
                    }

                }


            }

        }
    }
}
