package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.Features
import com.kabindra.clean.architecture.domain.entity.FeaturesUsed
import com.kabindra.clean.architecture.domain.entity.LoginUser
import com.kabindra.clean.architecture.domain.entity.LoginVerify
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginVerifyDTO(
    val response: LoginUserDTO?
) : BaseResponse()

@Serializable
data class LoginUserDTO(
    val token: String? = "",
    var refresh_token: String? = "",
    val user_details: UserDTO? = null,
    val features: FeaturesDTO? = null,
    val featuresUsed: FeaturesUsedDTO? = null
)

@Serializable
data class FeaturesDTO(
    val otp: Boolean? = false,
    val mpin: Boolean? = false,
    val biometric: Boolean? = false
)

@Serializable
data class FeaturesUsedDTO(
    val otp: Boolean? = false,
    val mpin: Boolean? = false,
    val biometric: Boolean? = false,
)

// Mapper function
fun LoginVerifyDTO.toDomain(): LoginVerify {
    return LoginVerify(
        response = response?.toDomain(),
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun LoginUserDTO.toDomain(): LoginUser {
    return LoginUser(
        token = token,
        refresh_token = refresh_token,
        user_details = user_details?.toDomain(),
        features = features?.toDomain(),
        featuresUsed = featuresUsed?.toDomain()
    )
}

fun FeaturesDTO.toDomain(): Features {
    return Features(
        otp = otp,
        mpin = mpin,
        biometric = biometric
    )
}

fun FeaturesUsedDTO.toDomain(): FeaturesUsed {
    return FeaturesUsed(
        otp = otp,
        mpin = mpin,
        biometric = biometric
    )
}

