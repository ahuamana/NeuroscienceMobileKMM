package com.ahuaman.neuroscienceandroiddevice.domain.usecase

import com.ahuaman.neuroscienceandroiddevice.domain.model.BluetoothDevice
import com.ahuaman.neuroscienceandroiddevice.domain.repository.BleRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.seconds

class ObserveScannedDevicesUseCase(
    private val bleRepository: BleRepository
) {
    operator fun invoke(): Flow<List<BluetoothDevice>> {
        //TODO: Add any additional logic if needed like filtering or sorting
        return bleRepository.scannedDevices
    }
}