package com.dfcoding.expensetracker

import android.app.Application
import com.dfcoding.expensetracker.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class CoinRoutineApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@CoinRoutineApplication)
        }
    }
}

