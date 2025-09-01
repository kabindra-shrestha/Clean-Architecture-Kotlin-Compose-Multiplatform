package com.kabindra.clean.architecture.utils.enums

enum class NotificationTypes(val title: String, val type: String) {
    ApplyTimeRequest("Apply Time Request", "apply-time-request"),
    ApplyLeaveRequest("Apply Leave Request", "apply-leave-request"),
    TicketDetail("Details", "detail"),
}

inline fun <reified T : Enum<T>> getNotificationTypes(type: String): NotificationTypes {
    return enumValues<T>().find { (it as NotificationTypes).type == type } as NotificationTypes
}
