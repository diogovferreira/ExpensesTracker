package com.dfcoding.expensetracker

import androidx.compose.ui.window.ComposeUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.dfcoding.expensetracker.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }