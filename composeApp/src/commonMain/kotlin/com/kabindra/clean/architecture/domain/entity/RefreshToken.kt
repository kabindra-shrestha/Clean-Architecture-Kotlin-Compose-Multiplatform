package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class RefreshToken(
    val response: RefreshTokenInfo?
) : BaseResponse()

@Serializable
data class RefreshTokenInfo(
    val token: String? = "",
    var refresh_token: String? = ""
)