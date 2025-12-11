package com.ahuaman.neuroscienceandroiddevice.domain.repository

import com.ahuaman.neuroscienceandroiddevice.domain.model.BluetoothDevice
import kotlinx.coroutines.flow.Flow

interface BleRepository {
    val scannedDevices: Flow<List<BluetoothDevice>>

    suspend fun startScan()
    suspend fun stopScan()
    suspend fun connect(deviceId:String)
}