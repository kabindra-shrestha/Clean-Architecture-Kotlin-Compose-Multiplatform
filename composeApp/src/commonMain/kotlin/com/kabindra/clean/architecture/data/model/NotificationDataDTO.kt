package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.Notification
import com.kabindra.clean.architecture.domain.entity.NotificationData
import com.kabindra.clean.architecture.domain.entity.NotificationInfo
import com.kabindra.clean.architecture.domain.entity.NotificationMeta
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDataDTO(
    val response: NotificationInfoDTO?
) : BaseResponse()

@Serializable
data class NotificationInfoDTO(
    val previous_notifications: List<NotificationDTO>? = listOf(),
    val unread_notifications: List<NotificationDTO>? = listOf()
)

@Serializable
data class NotificationDTO(
    val body: String? = "",
    val id: String? = "",
    val time: String? = "",
    val title: String? = "",
    val meta: NotificationMetaDTO? = null
)

@Serializable
data class NotificationMetaDTO(
    val ticket_id: Int? = 0,
    val type: String? = "",
    val workflow: String? = "",
    val date: String? = ""
)

fun NotificationDataDTO.toDomain(): NotificationData {
    return NotificationData(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun NotificationInfoDTO.toDomain(): NotificationInfo {
    return NotificationInfo(
        previous_notifications = previous_notifications?.map { it.toDomain() },
        unread_notifications = unread_notifications?.map { it.toDomain() },
    )
}

fun NotificationDTO.toDomain(): Notification {
    return Notification(
        body = body,
        id = id,
        time = time,
        title = title,
        meta = meta?.toDomain()
    )
}

fun NotificationMetaDTO.toDomain(): NotificationMeta {
    return NotificationMeta(
        ticket_id = ticket_id,
        type = type,
        workflow = workflow,
        date = date
    )
}


