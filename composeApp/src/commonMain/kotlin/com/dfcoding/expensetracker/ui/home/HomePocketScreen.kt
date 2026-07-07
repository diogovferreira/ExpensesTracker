package com.dfcoding.expensetracker.ui.home

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.dfcoding.expensetracker.domain.model.Pocket
import com.dfcoding.expensetracker.util.Formatter
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

class HomePocketScreen(
    private val onAddPocket: () -> Unit = {},
    private val onEditPocket: (Long) -> Unit = {},
    private val onDeletePocket: (Long) -> Unit = {},
    private val onPocketClick: (Pocket) -> Unit = {}
) : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<HomePocketViewModel>()

        HomeScreen(
            viewModel = viewModel,
            onAddPocket = onAddPocket,
            onEditPocket = onEditPocket,
            onDeletePocket = onDeletePocket,
            onPocketClick = onPocketClick

        )
    }
}

@Composable
fun HomeScreen(
    viewModel: HomePocketViewModel = koinViewModel(),
    onAddPocket: () -> Unit = {},
    onEditPocket: (Long) -> Unit = {},
    onDeletePocket: (Long) -> Unit = {},
    onPocketClick: (Pocket) -> Unit = {}
) {

    val pockets by viewModel.pockets.collectAsState()
    val numberOfExpenses by viewModel.totalNumberOfExpenses.collectAsState()
    val totalExpensesAmount by viewModel.totalAmount.collectAsState()


    HomeScreenStateless(
        pockets = pockets,
        expensesTotal = numberOfExpenses,
        expensesTotalAmount = totalExpensesAmount,
        onAddPocket = onAddPocket,
        onEditPocket = onEditPocket,
        onDeletePocket = onDeletePocket,
        onPocketClick = onPocketClick
    )
}

@Composable
fun HomeScreenStateless(
    pockets: List<Pocket>,
    expensesTotal: Double,
    expensesTotalAmount: Double,
    onAddPocket: () -> Unit = {},
    onEditPocket: (Long) -> Unit = {},
    onDeletePocket: (Long) -> Unit = {},
    onPocketClick: (Pocket) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val purpleGradient = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f) // fades out at bottom
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(purpleGradient)
        )

        //PURPLE AND WHITE CONTENT
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            //TOP PART -- PURPLE PART

            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Welcome Back \uD83D\uDC4B",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    fontFamily = FontFamily.Monospace,
                )
                Text(
                    text = "My Pockets",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                //Stats Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(16.dp)
                )
                {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "Total in Pockets",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "$expensesTotalAmount€",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 32.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )

                        HorizontalDivider(color = Color.White.copy(0.2f))
                        Spacer(modifier = Modifier.height(1.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Pockets",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    pockets.size.toString(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.displaySmall
                                )
                            }

                            VerticalDivider(
                                modifier = Modifier
                                    .height(40.dp)
                                    .padding(horizontal = 16.dp),
                                color = Color.White.copy(alpha = 0.2f)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Expenses",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = expensesTotal.toInt().toString(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.displaySmall,
                                    fontFamily = FontFamily.Monospace,
                                )
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(10.dp)) // add this - increase until it looks right

            }

            //WHITE PART////////////////////////
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Transparent)
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 4.dp)
                ) {
                    //Header
                    Text(
                        "All Pockets",
                        style = MaterialTheme.typography.labelLarge,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
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

        }

    }
}


@Composable
fun PocketItem(
    pocket: Pocket,
    onDeletePocket: (Long) -> Unit,
    onEditPocket: (Long) -> Unit,
    onPocketClick: (Pocket) -> Unit
) {

    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onPocketClick(pocket) },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(0.2f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.background),
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
                    Text(
                        text = pocket.name, style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = Formatter.formatDate(pocket.date),
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Monospace,
                    )
                }

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


@Composable
fun HomeBottomBar(
    currentRoute: String = "home",
    onNavigateHome: () -> Unit = {},
    onNavigateStats: () -> Unit = {},
    onNavigateAdd: () -> Unit = {},
    onNavigateHistory: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = onNavigateHome,
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute == "stats",
            onClick = onNavigateStats,
            icon = { Icon(Icons.Default.BarChart, contentDescription = null) },
            label = { Text("Stats") }
        )
        NavigationBarItem(
            selected = false,
            onClick = onNavigateAdd,
            icon = {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                }
            },
            label = { Text("") }
        )
        NavigationBarItem(
            selected = currentRoute == "history",
            onClick = onNavigateHistory,
            icon = { Icon(Icons.Default.History, contentDescription = null) },
            label = { Text("History") }
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = onNavigateProfile,
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreenStateless(
            pockets = listOf(
                Pocket(id = 1, name = "Home Expenses", icon = "🏠", date = 0L, currency = "USD"),
                Pocket(id = 2, name = "Transport", icon = "🚗", date = 0L, currency = "EUR")
            ),
            expensesTotal = 100.0,
            expensesTotalAmount = 200.0
        )
    }
}