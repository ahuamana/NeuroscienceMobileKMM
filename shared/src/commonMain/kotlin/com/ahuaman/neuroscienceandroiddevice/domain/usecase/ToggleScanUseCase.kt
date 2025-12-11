package com.ahuaman.neuroscienceandroiddevice.domain.usecase

import com.ahuaman.neuroscienceandroiddevice.domain.repository.BleRepository

class ToggleScanUseCase(
    private val bleRepository: BleRepository
) {

    suspend operator fun invoke(isCurrentlyScanning: Boolean) {
        if (isCurrentlyScanning) {
            bleRepository.stopScan()
        } else {
            bleRepository.startScan()
        }
    }
}