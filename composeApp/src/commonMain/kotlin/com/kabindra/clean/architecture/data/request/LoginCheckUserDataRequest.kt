package com.kabindra.clean.architecture.data.request

data class LoginCheckUserDataRequest(
    val username: String,
    val appLoginCode: String,
    val fcmToken: String
)