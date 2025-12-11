package com.ahuaman.neuroscienceandroiddevice.domain.repository

import com.ahuaman.neuroscienceandroiddevice.domain.model.BluetoothDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class BleRepositoryImpl(
    //TODO: Inject dependencies here
): BleRepository {
    private val _scannedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    override val scannedDevices: Flow<List<BluetoothDevice>> = _scannedDevices

    override suspend fun startScan() {
        //TODO: Implement BLE scanning logic here
    }

    override suspend fun stopScan() {
        //TODO: Implement logic to stop BLE scanning here
    }

    override suspend fun connect(deviceId: String) {
        //TODO: Connection logic to the device with the given deviceId
    }
}