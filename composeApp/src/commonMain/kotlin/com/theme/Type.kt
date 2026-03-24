package com.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import expensetracker.composeapp.generated.resources.Res
import expensetracker.composeapp.generated.resources.inter_bold
import expensetracker.composeapp.generated.resources.inter_medium
import expensetracker.composeapp.generated.resources.inter_regular
import expensetracker.composeapp.generated.resources.inter_semibold
import org.jetbrains.compose.resources.Font

val InterFontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.inter_regular, FontWeight.Normal),
        Font(Res.font.inter_medium, FontWeight.Medium),
        Font(Res.font.inter_semibold, FontWeight.SemiBold),
        Font(Res.font.inter_bold, FontWeight.Bold)
    )