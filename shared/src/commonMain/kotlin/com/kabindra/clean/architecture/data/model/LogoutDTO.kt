package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.Logout
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LogoutDTO(
    val response: String? = ""
) : BaseResponse()

fun LogoutDTO.toDomain(): Logout {
    return Logout(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}


