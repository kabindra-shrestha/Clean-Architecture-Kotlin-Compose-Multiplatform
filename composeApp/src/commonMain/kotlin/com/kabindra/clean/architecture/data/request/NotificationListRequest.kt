package com.kabindra.clean.architecture.data.request

data class NotificationListRequest(
    val unread_notifications: String,
    val previous_notifications: String
)