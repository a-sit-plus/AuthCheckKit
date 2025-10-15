package at.asitplus.authcheckkit

actual object AuthCheckKit {
    private val checkLib = IosCheckLib()
    actual fun getDeviceStatus(): AuthCheckKitStatus = checkLib.getDeviceStatus()
}