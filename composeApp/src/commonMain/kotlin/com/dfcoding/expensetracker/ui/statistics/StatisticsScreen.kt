package com.dfcoding.expensetracker.ui.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dfcoding.expensetracker.ui.components.LongButton
import com.dfcoding.expensetracker.ui.expenses.ExpenseItem
import com.dfcoding.expensetracker.ui.expenses.ExpenseListHeader
import com.dfcoding.expensetracker.ui.expenses.ExpenseListItem
import com.dfcoding.expensetracker.ui.home.HomePocketViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

class StatisticsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<StatisticsScreenViewModel>()

        StatisticsContent(viewModel)

    }


}

@Composable
fun StatisticsContent(viewModel: StatisticsScreenViewModel) {


    StatisticsContentStateless()

}

@Preview
@Composable
fun StatisticsContentStateless() {
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
            text = "Statistics",
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(100.dp))

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


                // LongButton("Add Expense", onButtonClick = onAddExpense, isEnabled = true)
            }
        }
    }
}