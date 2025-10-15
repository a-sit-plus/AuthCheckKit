import at.asitplus.authcheckkit.BiometricSecurityClass

/**
 * @suppress
 */
interface CheckLib {
    fun isDeviceLockSet(): Boolean
    fun isFingerprintSupported(): Boolean
    fun isFaceAuthenticationSupported(): Boolean
    fun getBiometryEnrollment(): BiometricSecurityClass
}