package com.kabindra.clean.architecture.presentation.viewmodel.remote

import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest

sealed class SplashEvent {
    data class GetLoginRefreshUserDetails(
        val loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest
    ) : SplashEvent()

    data object GetIsLogged : SplashEvent()

    data object GetUser : SplashEvent()
}