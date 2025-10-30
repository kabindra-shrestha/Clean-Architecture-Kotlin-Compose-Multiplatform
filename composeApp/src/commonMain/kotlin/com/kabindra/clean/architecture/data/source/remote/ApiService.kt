package com.kabindra.clean.architecture.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parameters

class ApiService(private val client: HttpClient) {

    suspend fun getLoginCheckUser(
        username: String,
        appLoginCode: String,
        fcmToken: String
    ): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_LOGIN_CHECK_USER,
            formParameters = parameters {
                append("username", username)
                append("app_login_code", appLoginCode)
                append("fcm_token", fcmToken)
            }
        )
    }

    suspend fun getLoginSendOTP(
        username: String,
        appLoginCode: String,
        otp: String,
        fcmToken: String
    ): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_LOGIN_VERIFY_OTP,
            formParameters = parameters {
                append("username", username)
                append("app_login_code", appLoginCode)
                append("otp", otp)
                append("fcm_token", fcmToken)
            }
        )
    }

    suspend fun getLoginRefreshUserDetails(fcmToken: String): HttpResponse {
        return client.get(ApiEndpoints.API_LOGIN_REFRESH_USER_DETAILS) {
            url {
                parameters.append("fcm_token", fcmToken)
            }
        }
    }

    suspend fun getRefreshToken(refreshToken: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_REFRESH_TOKEN,
            formParameters = parameters {
                append("refresh_token", refreshToken)
            }
        ) {
            // This cannot be called here as it depend on Auth module of Ktor
            // That's why this API is used on Auth module
            // markAsRefreshTokenRequest()
        }
    }

    suspend fun getLogout(): HttpResponse {
        return client.get(ApiEndpoints.API_LOGOUT)
    }

}