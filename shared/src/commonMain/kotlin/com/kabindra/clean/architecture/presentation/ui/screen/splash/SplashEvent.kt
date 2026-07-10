package com.kabindra.clean.architecture.presentation.ui.screen.splash

import com.kabindra.clean.architecture.utils.constants.AlertType
import com.kabindra.clean.architecture.utils.constants.MessageType
import com.kabindra.clean.architecture.utils.constants.ResponseType

sealed interface SplashEvent {
    data class ShowMessage(
        val messageType: MessageType = MessageType.Success,
        val responseType: ResponseType = ResponseType.None,
        val alertType: AlertType = AlertType.None,
        val title: String = "",
        val message: String = "",
        val statusCode: Int = -1
    ) : SplashEvent

    data object OnNavigateLogin : SplashEvent

    data object OnNavigateDashboard : SplashEvent
}