package com.kabindra.clean.architecture.utils.handler

import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_CODE_HANDLED
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_CODE_NOT_HANDLED
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_TOKEN_INVALID_EXPIRED
import org.koin.core.component.KoinComponent

class HandleResponseStatusCode(
    val statusCode: Int = -1,
    val onNavigateLogin: () -> Unit,
) : KoinComponent {

    fun statusCodeHandler(): Int {
        when (statusCode) {
            STATUS_TOKEN_INVALID_EXPIRED -> {
                AuthenticationHandler().logout(onNavigateLogin = { onNavigateLogin() })

                return STATUS_CODE_HANDLED
            }

            /*STATUS_GEO_ACCESS_DENIED -> {
                return STATUS_CODE_HANDLED
            }

            STATUS_VALIDATION -> {
                return STATUS_CODE_HANDLED
            }

            STATUS_TOO_MANY_REQUESTS -> {
                return STATUS_CODE_HANDLED
            }

            STATUS_PACKAGE_EXPIRED -> {
                return STATUS_CODE_NOT_HANDLED
            }

            STATUS_VERSION_MISMATCHED -> {
                return STATUS_CODE_HANDLED
            }*/

            else -> {
                return STATUS_CODE_NOT_HANDLED
            }
        }
    }

}