package com.lokal.otpauthapp

import android.app.Application
import com.lokal.otpauthapp.analytics.AnalyticsLogger
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val isDebug = true
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        }

        AnalyticsLogger.init()
    }
}


