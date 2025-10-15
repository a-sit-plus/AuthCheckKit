package at.asitplus.authcheckkit.testapp

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import at.asitplus.authcheckkit.AuthCheckKit
import kotlin.test.Test
import kotlin.test.assertNotNull

class CommonTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun deviceStatusTest() = runComposeUiTest {
        assertNotNull(AuthCheckKit.getDeviceStatus())
    }
}