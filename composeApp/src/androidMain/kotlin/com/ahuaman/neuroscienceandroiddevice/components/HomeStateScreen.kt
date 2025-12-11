package com.ahuaman.neuroscienceandroiddevice.components

import android.content.pm.PackageManager
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ahuaman.neuroscienceandroiddevice.permissions.getRequiredPermissions


@Composable
fun HomeStateScreen(
    modifier: Modifier = Modifier,
     description: String,
    onGrantedPermissions: () -> Unit) {

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        // Check if all permissions were granted
        val allGranted = perms.values.all { it }

        if (allGranted) {
            // SUCCESS: Safe to call the KMM ViewModel
            onGrantedPermissions()
        } else {
            // FAILURE: Show explanation
            Toast.makeText(context, "Bluetooth permissions are required", Toast.LENGTH_SHORT).show()
        }
    }

    val onScanClick = {
        val permissions = getRequiredPermissions()
        val hasPermissions = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (hasPermissions) {
            onGrantedPermissions()
        } else {
            permissionLauncher.launch(permissions) // Ask user
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(description)
        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick =  onScanClick
        ) {
            Text("Start Scan" )
        }
    }

}


@Preview
@Composable
fun HomeStateScreenPreview() {
    HomeStateScreen(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        description = "This is a preview of HomeStateScreen",
        onGrantedPermissions = {}
    )
}