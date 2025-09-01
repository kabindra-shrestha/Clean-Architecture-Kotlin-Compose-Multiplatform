package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.NotificationMarkAsRead
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class NotificationMarkAsReadDTO(
    val response: String? = ""
) : BaseResponse()

fun NotificationMarkAsReadDTO.toDomain(): NotificationMarkAsRead {
    return NotificationMarkAsRead(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}


