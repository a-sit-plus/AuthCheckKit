package at.asitplus.authcheckkit

import CheckLib
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.biometric.BiometricManager

class AndroidCheckLib() : CheckLib {
    val context: () -> Context = { AppLifecycleMonitor.currentActivity as Context }

    /**
     * Checks whether the device is secured with a Lock (PIN/Password/Pattern).
     * Biometric factors can only be enrolled on devices with a lock.
     *
     * @return **true**, if the device is secured with a PIN/Password/Pattern.
     */
    override fun isDeviceLockSet(): Boolean =
        (context().getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager)?.isDeviceSecure ?: false

    /**
     * Checks whether the device supports fingerprint authentication.
     *
     * @return **true**, if the device supports fingerprint authentication.
     */
    override fun isFingerprintSupported(): Boolean = context().packageManager.let { packageManager ->
        packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }


    /**
     * Checks whether the device supports face authentication.
     *
     * @return **true**, if the device supports face authentication.
     */
    override fun isFaceAuthenticationSupported(): Boolean = context().packageManager.let { packageManager ->
        packageManager.hasSystemFeature(PackageManager.FEATURE_FACE)
    }

    /**
     * Checks whether the user has enrolled biometric authentication and returns the security class.
     * Does not reveal the type of the biometric authentication
     *
     * @return **BiometricSecurityClass**
     */
    override fun getBiometryEnrollment(): BiometricSecurityClass =
        BiometricManager.from(context()).let { biometricManager ->
            listOf(
                BiometricManager.Authenticators.BIOMETRIC_STRONG to BiometricSecurityClass.CLASS3,
                BiometricManager.Authenticators.BIOMETRIC_WEAK to BiometricSecurityClass.CLASS2
            ).let { list ->
                return list.firstOrNull { (authenticator, _) ->
                    biometricManager.canAuthenticate(authenticator) == BiometricManager.BIOMETRIC_SUCCESS
                }?.second ?: BiometricSecurityClass.NONE
            }
        }

    /**
     * Retrieves the current status of the device.
     *
     * @return **AuthCheckKitStatus**
     */
    fun getDeviceStatus(): AuthCheckKitStatus = AuthCheckKitStatus(
        isDeviceLockSet = isDeviceLockSet(),
        isFingerprintSupported = isFingerprintSupported(),
        isFaceAuthenticationSupported = isFaceAuthenticationSupported(),
        biometryEnrollment = getBiometryEnrollment()
    )
}