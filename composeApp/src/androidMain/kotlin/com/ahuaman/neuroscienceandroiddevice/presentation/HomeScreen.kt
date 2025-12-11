package com.ahuaman.neuroscienceandroiddevice.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ahuaman.neuroscienceandroiddevice.components.HomeStateScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier.fillMaxSize()) {

    val viewModel: DeviceListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    when(state.uiState){
        UIState.IDLE -> {
            Log.d("HomeScreen", "State is IDLE")
            HomeStateScreen(
                modifier = modifier,
                description = "Welcome! Please connect a device.",
                onGrantedPermissions = {
                    viewModel.onScanClicked()
                }
            )
        }
        UIState.LOADING -> {
            Log.d("HomeScreen", "State is LOADING")
            HomeStateScreen(
                modifier = modifier,
                description = "Loading devices, please wait..." ,
                onGrantedPermissions = {
                    viewModel.onScanClicked()
                }
            )
        }
        UIState.SUCCESS -> {
            Log.d("HomeScreen", "State is SUCCESS")
            HomeStateScreen(
                modifier = modifier,
                description = "Devices loaded successfully!",
                onGrantedPermissions = {
                    viewModel.onScanClicked()
                }
            )
        }
        UIState.ERROR -> {
            HomeStateScreen(
                modifier = modifier,
                description = "An error occurred while loading devices.",
                onGrantedPermissions = {
                    viewModel.onScanClicked()
                }
            )
        }
    }

}