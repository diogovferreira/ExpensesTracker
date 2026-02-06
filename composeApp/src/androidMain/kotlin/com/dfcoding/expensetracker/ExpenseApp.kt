package com.dfcoding.expensetracker

import android.app.Application
import com.dfcoding.expensetracker.di.appModules
import com.dfcoding.expensetracker.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ExpenseApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ExpenseApp)
            modules(platformModule + appModules)
        }
    }
}

