package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsAllRead
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class NotificationMarkAsAllReadDTO(
    val response: String? = ""
) : BaseResponse()

fun NotificationMarkAsAllReadDTO.toDomain(): NotificationMarkAsAllRead {
    return NotificationMarkAsAllRead(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}


