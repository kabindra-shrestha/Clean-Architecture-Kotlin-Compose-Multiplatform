package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginVerify(
    val response: LoginUser?,
) : BaseResponse()

@Serializable
data class LoginUser(
    val token: String? = "",
    var refresh_token: String? = "",
    val user_details: User?,
    val features: Features?,
    val featuresUsed: FeaturesUsed?
)

@Serializable
data class ApiToken(
    var token: String? = "",
    var refresh_token: String? = ""
)

@Serializable
data class User(
    val name: String? = "",
    val code: String? = "",
    val department: String? = "",
    val branch: String? = "",
    val contact: String? = "",
    val email: String? = "",
    val username: String? = "",
    var profile_picture: String? = "",
    val firebase_topics: List<String> = listOf(),
)

@Serializable
data class Features(
    val otp: Boolean? = false,
    val mpin: Boolean? = false,
    val biometric: Boolean? = false
)

@Serializable
data class FeaturesUsed(
    val otp: Boolean? = false,
    val mpin: Boolean? = false,
    val biometric: Boolean? = false,
)