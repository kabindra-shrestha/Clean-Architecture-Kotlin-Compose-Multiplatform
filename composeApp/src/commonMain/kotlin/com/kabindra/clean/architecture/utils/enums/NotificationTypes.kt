package com.kabindra.clean.architecture.utils.enums

enum class NotificationTypes(val title: String, val type: String) {
    Notification("Notification", "notification"),
}

inline fun <reified T : Enum<T>> getNotificationTypes(type: String): NotificationTypes {
    return enumValues<T>().find { (it as NotificationTypes).type == type } as NotificationTypes
}
