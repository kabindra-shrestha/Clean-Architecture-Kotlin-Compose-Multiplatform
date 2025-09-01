package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class NotificationData(
    val response: NotificationInfo?
) : BaseResponse()

@Serializable
data class NotificationInfo(
    val previous_notifications: List<Notification>? = listOf(),
    val unread_notifications: List<Notification>? = listOf()
)

@Serializable
data class Notification(
    val id: String? = "",
    val title: String? = "",
    val body: String? = "",
    val time: String? = "",
    var isUnread: Boolean? = false,
    val meta: NotificationMeta? = null,
)

@Serializable
data class NotificationMeta(
    val ticket_id: Int? = 0,
    val type: String? = "",
    val workflow: String? = "",
    val date: String? = ""

)