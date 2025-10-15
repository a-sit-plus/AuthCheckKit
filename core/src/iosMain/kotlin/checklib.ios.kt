package at.asitplus.authcheckkit

import CheckLib
import kotlinx.cinterop.*
import platform.Foundation.NSError
import platform.LocalAuthentication.*

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IosCheckLib() : CheckLib {
    /**
     * Checks whether the device is secured with a Lock (PIN/Password).
     * Biometric factors can only be enrolled on devices with a lock.
     *
     * @return **true**, if the device is secured with a PIN/Password.
     */
    override fun isDeviceLockSet(): Boolean {
        LAContext().use { context ->
            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                val canEvaluate = context.canEvaluatePolicy(
                    policy = LAPolicyDeviceOwnerAuthentication, error = errorPtr.ptr
                )
                return canEvaluate
            }
        }
    }

    /**
     * Checks whether the device supports fingerprint authentication.
     *
     * @return **true**, if the device supports fingerprint authentication.
     */
    override fun isFingerprintSupported(): Boolean {
        LAContext().use { context ->
            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                context.canEvaluatePolicy(
                    policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics, error = errorPtr.ptr
                )
                return context.biometryType == LABiometryTypeTouchID
            }
        }
    }

    /**
     * Checks whether the device supports face authentication.
     *
     * @return **true**, if the device supports face authentication.
     */
    override fun isFaceAuthenticationSupported(): Boolean {
        LAContext().use { context ->
            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                context.canEvaluatePolicy(
                    policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics, error = errorPtr.ptr
                )
                return context.biometryType == LABiometryTypeFaceID
            }
        }
    }

    /**
     * Checks whether the user has enrolled biometric authentication and returns the security class.
     * Does not reveal the type of the biometric authentication
     *
     * @return **BiometricSecurityClass**
     */
    override fun getBiometryEnrollment(): BiometricSecurityClass = when (isTouchIDEnrolled() || isFaceIDEnrolled()) {
        true -> BiometricSecurityClass.CLASS3
        else -> BiometricSecurityClass.NONE
    }

    fun isTouchIDEnrolled(): Boolean {
        LAContext().use { context ->
            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                val canEvaluate = context.canEvaluatePolicy(
                    policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics, error = errorPtr.ptr
                )
                return canEvaluate && context.biometryType == LABiometryTypeTouchID
            }
        }
    }

    fun isFaceIDEnrolled(): Boolean {
        LAContext().use { context ->
            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                val canEvaluate = context.canEvaluatePolicy(
                    policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics, error = errorPtr.ptr
                )
                if (canEvaluate && context.biometryType == LABiometryTypeFaceID) {
                    return true
                }
                errorPtr.value?.let { error ->
                    when (error.code) {
                        LAErrorBiometryNotEnrolled -> return false
                        LAErrorBiometryNotAvailable -> return true
                    }
                }
                return false
            }
        }
    }

    /**
     * Retrieves the current status of the device.
     *
     * @return **AuthCheckKitStatus**
     */
    fun getDeviceStatus(): AuthCheckKitStatus {
        return AuthCheckKitStatus(
            isDeviceLockSet = isDeviceLockSet(),
            isFingerprintSupported = isFingerprintSupported(),
            isFaceAuthenticationSupported = isFaceAuthenticationSupported(),
            biometryEnrollment = getBiometryEnrollment()
        )
    }
}

inline fun <R> LAContext.use(block: (LAContext) -> R): R {
    try {
        return block(this)
    } finally {
        this.invalidate()
    }
}
