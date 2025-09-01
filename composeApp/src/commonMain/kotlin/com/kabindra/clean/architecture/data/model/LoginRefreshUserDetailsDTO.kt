package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.LoginRefreshUserDetails
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginRefreshUserDetailsDTO(
    val response: LoginUserDTO?
) : BaseResponse()

// Mapper function
fun LoginRefreshUserDetailsDTO.toDomain(): LoginRefreshUserDetails {
    return LoginRefreshUserDetails(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}