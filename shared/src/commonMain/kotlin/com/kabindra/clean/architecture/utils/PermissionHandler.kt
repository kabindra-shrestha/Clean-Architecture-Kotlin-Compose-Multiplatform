package com.kabindra.clean.architecture.utils

enum class PermissionType {
    NOTIFICATION,
    CAMERA,
    LOCATION,
    STORAGE,
    MICROPHONE
}

expect object PermissionHandler {

    fun requestPermission(
        permission: PermissionType,
        callback: (Boolean) -> Unit
    )

    fun hasPermission(
        permission: PermissionType
    ): Boolean
}