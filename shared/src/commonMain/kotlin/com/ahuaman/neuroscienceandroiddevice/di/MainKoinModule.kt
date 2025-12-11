package com.ahuaman.neuroscienceandroiddevice.di

import com.ahuaman.neuroscienceandroiddevice.domain.repository.BleRepository
import com.ahuaman.neuroscienceandroiddevice.domain.repository.BleRepositoryImpl
import com.ahuaman.neuroscienceandroiddevice.domain.usecase.ObserveScannedDevicesUseCase
import com.ahuaman.neuroscienceandroiddevice.domain.usecase.ToggleScanUseCase
import com.ahuaman.neuroscienceandroiddevice.presentation.DeviceListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModule() = module {
    //1. Data layer
    single<BleRepository> { BleRepositoryImpl() }

    //2. Domain layer -- (factories for use cases can be added here)
    factory { ObserveScannedDevicesUseCase(get()) }
    factory { ToggleScanUseCase(get()) }

    //3. Presentation layer
    factory {
        DeviceListViewModel(
            observeScannedDevicesUseCase = get(),
            toggleScanUseCase = get()
        )
    }
}