package com.ahuaman.neuroscienceandroiddevice.domain.repository

import com.ahuaman.neuroscienceandroiddevice.domain.model.BluetoothDevice
import com.juul.kable.Advertisement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.juul.kable.Scanner
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BleRepositoryImpl(
    //TODO: Inject dependencies here
): BleRepository {

    // 1. Internal state to hold unique devices found
    private val _scannedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    override val scannedDevices: Flow<List<BluetoothDevice>> = _scannedDevices

    //Map to prevent duplicates (Key: deviceId, Value: BluetoothDevice)
    private val foundDevicesMap = mutableMapOf<String, BluetoothDevice>()

    // Scope for background BLE tasks
    private val scope = CoroutineScope(Dispatchers.Default)
    private var scanJob: Job? = null

    //Kable scanner
    private val scanner = Scanner {

    }

    override suspend fun startScan() {
        if(scanJob?.isActive == true) return

        // Clear previous devices
        foundDevicesMap.clear()

        _scannedDevices.value = emptyList()

        scanJob = scanner
            .advertisements
            .catch {
                println("Error during scanning: ${it.message}")
            }.onEach {advertisement ->
                handledAdvertisement(advertisement)
            }.launchIn(scope)
    }

    override suspend fun stopScan() {
        scanJob?.cancel()
        scanJob = null
    }

    override suspend fun connect(deviceId: String) {
        //TODO: Connection logic to the device with the given deviceId
    }

    private fun handledAdvertisement(advertisement:Advertisement) {
        val newDevice = advertisement.toDomainModel()
        foundDevicesMap[newDevice.id] = newDevice
        _scannedDevices.value = foundDevicesMap.values.toList()
    }

    private fun Advertisement.toDomainModel(): BluetoothDevice {
        val deviceId = this@toDomainModel.identifier.toString()
        val deviceName = this@toDomainModel.name ?: "Unknown Device"
        val rssi = this@toDomainModel.rssi
        return BluetoothDevice(
            id = deviceId,
            name = deviceName,
            rssi = rssi
        )
    }
}