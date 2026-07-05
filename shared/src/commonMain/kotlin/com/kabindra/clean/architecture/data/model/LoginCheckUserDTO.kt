package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.LoginCheckUser
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginCheckUserDTO(
    val response: LoginUserDTO?
) : BaseResponse()

// Mapper function
fun LoginCheckUserDTO.toDomain(): LoginCheckUser {
    return LoginCheckUser(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}