package com.kabindra.clean.architecture.data.source.remote

import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.data.request.LoginSendOTPDataRequest
import com.kabindra.clean.architecture.data.request.RefreshTokenDataRequest
import io.ktor.client.statement.HttpResponse

class ApiDataSource(private val apiService: ApiService) {

    suspend fun getLoginCheckUser(loginCheckUserDataRequest: LoginCheckUserDataRequest): HttpResponse {
        return apiService.getLoginCheckUser(
            loginCheckUserDataRequest.username,
            loginCheckUserDataRequest.appLoginCode,
            loginCheckUserDataRequest.fcmToken
        )
    }

    suspend fun getLoginSendOTP(loginSendOTPDataRequest: LoginSendOTPDataRequest): HttpResponse {
        return apiService.getLoginSendOTP(
            loginSendOTPDataRequest.username,
            loginSendOTPDataRequest.appLoginCode,
            loginSendOTPDataRequest.otp,
            loginSendOTPDataRequest.fcmToken
        )
    }

    suspend fun getLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest): HttpResponse {
        return apiService.getLoginRefreshUserDetails(
            loginRefreshUserDetailsDataRequest.fcmToken
        )
    }

    suspend fun getRefreshToken(refreshTokenDataRequest: RefreshTokenDataRequest): HttpResponse {
        return apiService.getRefreshToken(
            refreshTokenDataRequest.refreshToken
        )
    }

    suspend fun getLogout(): HttpResponse {
        return apiService.getLogout()
    }


}