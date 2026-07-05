package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.RefreshToken
import com.kabindra.clean.architecture.domain.entity.RefreshTokenInfo
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDTO(
    val response: RefreshTokenInfoDTO?
) : BaseResponse()

@Serializable
data class RefreshTokenInfoDTO(
    val token: String? = "",
    var refresh_token: String? = ""
)

// Mapper function
fun RefreshTokenDTO.toDomain(): RefreshToken {
    return RefreshToken(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun RefreshTokenInfoDTO.toDomain(): RefreshTokenInfo {
    return RefreshTokenInfo(
        token = token,
        refresh_token = refresh_token
    )
}