package com.kabindra.clean.architecture.presentation.viewmodel.remote

import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.data.request.LoginSendOTPDataRequest

sealed class LoginEvent {
    data class GetLoginCheckUser(
        val loginCheckUserDataRequest: LoginCheckUserDataRequest
    ) : LoginEvent()

    data class GetLoginVerifyOTP(
        val loginSendOTPRequest: LoginSendOTPDataRequest
    ) : LoginEvent()

    data class GetLoginRefreshUserDetails(
        val loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest
    ) : LoginEvent()
}