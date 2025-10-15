package at.asitplus.authcheckkit

actual object AuthCheckKit {
    private val checkLib = AndroidCheckLib()
    actual fun getDeviceStatus(): AuthCheckKitStatus = checkLib.getDeviceStatus()
}