package com.kabindra.clean.architecture.utils

actual object PermissionHandler {

    actual fun requestPermission(
        permission: PermissionType,
        callback: (Boolean) -> Unit
    ) {
        // Implement iOS permission APIs here
        callback(true)
    }

    actual fun hasPermission(
        permission: PermissionType
    ): Boolean {
        return true
    }

}