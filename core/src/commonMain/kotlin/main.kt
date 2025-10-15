package at.asitplus.authcheckkit

/**
 * AuthCheckKit service object
 */
expect object AuthCheckKit {
    /**
     * Method to retrieve the current device status
     */
    fun getDeviceStatus(): AuthCheckKitStatus
}

/**
 * Representation of the current device status
 *
 * @property isDeviceLockSet Status whether device lock is set.
 * @property isFingerprintSupported Status whether authentication with fingerprint ist supported on the device.
 * @property isFaceAuthenticationSupported Status whether authentication with face ist supported on the device.
 * @property biometryEnrollment Retrieves the security class of the enrolled biometric authentication.
 */
data class AuthCheckKitStatus(
    val isDeviceLockSet: Boolean,
    val isFingerprintSupported: Boolean,
    val isFaceAuthenticationSupported: Boolean,
    val biometryEnrollment: BiometricSecurityClass
)

/**
 * Represents the security class of the enrolled biometric authentication based on the Android CCD.
 * Note: Biometric authentication on iOS always maps to class 3 of the Android CCD.
 */
enum class BiometricSecurityClass {
    /**
     * No biometric authentication is enrolled
     */
    NONE,

    /**
     * Class 2 biometric authentication enrolled on the device
     */
    CLASS2,

    /**
     * Class 3 biometric authentication enrolled on the device
     */
    CLASS3
}