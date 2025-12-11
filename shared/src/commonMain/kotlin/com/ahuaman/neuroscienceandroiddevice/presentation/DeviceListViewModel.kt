package com.ahuaman.neuroscienceandroiddevice.presentation

import com.ahuaman.neuroscienceandroiddevice.domain.usecase.ObserveScannedDevicesUseCase
import com.ahuaman.neuroscienceandroiddevice.domain.usecase.ToggleScanUseCase
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class DeviceListViewModel(
    private val observeScannedDevicesUseCase: ObserveScannedDevicesUseCase,
    private val toggleScanUseCase: ToggleScanUseCase
):  ViewModel(){
    //UIState

    private val _state = MutableStateFlow<DeviceUiState>(viewModelScope, DeviceUiState.default())

    val state = _state.asStateFlow() // TODO: Integrate @NativeCoroutinesState to support cancellations in SwiftUI

    init {
       observeDevices()
    }

    private fun observeDevices() {
        viewModelScope.launch {
            println("Starting to observe scanned devices...")
            _state.update {
                it.copy(uiState = UIState.LOADING)
            }

            observeScannedDevicesUseCase().collect { devices ->
                _state.update {
                    it.copy(devices = devices, uiState = UIState.SUCCESS)
                }
            }
        }
    }

    fun onScanClicked() {
        viewModelScope.launch {
            val isCurrentlyScanning = _state.value.isScanning
            toggleScanUseCase(isCurrentlyScanning)

            //Optimistic update of scanning state -- TODO: Update based on actual scan state from repository
            _state.update {
                it.copy(uiState = UIState.LOADING, isScanning = !isCurrentlyScanning)
            }
        }
    }
}


data class DeviceUiState(
    val isScanning: Boolean = false,
    val devices: List<com.ahuaman.neuroscienceandroiddevice.domain.model.BluetoothDevice> = emptyList(),
    val uiState: UIState,
) {
    companion object {
        fun default() = DeviceUiState(
            isScanning = false,
            devices = emptyList(),
            uiState = UIState.default()
        )
    }
}

enum class UIState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR;

    companion object {

        fun default() = IDLE
    }
}