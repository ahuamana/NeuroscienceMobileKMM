package com.ahuaman.neuroscienceandroiddevice.app

import android.app.Application
import com.ahuaman.neuroscienceandroiddevice.di.appModule
import com.ahuaman.neuroscienceandroiddevice.di.initKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize things here if needed
        initKoin()
    }
}