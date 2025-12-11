package com.ahuaman.neuroscienceandroiddevice.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}