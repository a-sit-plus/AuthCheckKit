package at.asitplus.authcheckkit.testapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import at.asitplus.authcheckkit.AuthCheckKit

@Composable
fun App() {
    val status = AuthCheckKit.getDeviceStatus()
    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            Text("isDeviceLockSet: ${status?.isDeviceLockSet}")
            Text("biometryEnrollment: ${status?.biometryEnrollment}")
            Text("isFingerprintSupported: ${status?.isFingerprintSupported}")
            Text("isFaceAuthenticationSupported: ${status?.isFaceAuthenticationSupported}")

        }
    }
}