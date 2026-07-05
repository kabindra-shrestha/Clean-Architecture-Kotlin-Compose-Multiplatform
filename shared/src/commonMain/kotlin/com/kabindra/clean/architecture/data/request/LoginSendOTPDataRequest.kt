package com.kabindra.clean.architecture.data.request

data class LoginSendOTPDataRequest(
    val username: String,
    val appLoginCode: String,
    val otp: String,
    val fcmToken: String
)