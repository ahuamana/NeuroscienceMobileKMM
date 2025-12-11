package com.ahuaman.neuroscienceandroiddevice.presentation

import com.ahuaman.neuroscienceandroiddevice.domain.usecase.ObserveScannedDevicesUseCase
import com.ahuaman.neuroscienceandroiddevice.domain.usecase.ToggleScanUseCase
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.flow.asStateFlow

class DeviceListViewModel(
    private val observeScannedDevicesUseCase: ObserveScannedDevicesUseCase,
    private val toggleScanUseCase: ToggleScanUseCase
):  ViewModel(){
    //UIState

    private val _state = MutableStateFlow<DeviceUiState?>(viewModelScope, null)
    val state = _state.asStateFlow() // TODO: Integrate @NativeCoroutinesState to support cancellations in SwiftUI

    init {
        observeDevices()
    }

    private fun observeDevices() {
        viewModelScope.launch {
            observeScannedDevicesUseCase().collect { devices ->
                _state.value = _state.value?.copy(devices = devices)
            }
        }
    }

    fun onScanClicked() {
        viewModelScope.launch {
            val isCurrentlyScanning = _state.value?.isScanning
            toggleScanUseCase(isCurrentlyScanning == true)

            //Optimistic update of scanning state -- TODO: Update based on actual scan state from repository
            _state.value = _state.value?.copy(isScanning = isCurrentlyScanning != true)
        }
    }
}


data class DeviceUiState(
    val isScanning: Boolean = false,
    val devices: List<com.ahuaman.neuroscienceandroiddevice.domain.model.BluetoothDevice> = emptyList()
)