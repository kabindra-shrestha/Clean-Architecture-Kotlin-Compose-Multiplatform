package com.kabindra.clean.architecture.presentation.ui.screen.splash

import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest

sealed interface SplashAction {
    data class GetLoginRefreshUserDetails(
        val loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest
    ) : SplashAction

    data object GetIsLogged : SplashAction

    data object GetUser : SplashAction

    data object OnNavigateLogin : SplashAction

    data object OnNavigateDashboard : SplashAction
}