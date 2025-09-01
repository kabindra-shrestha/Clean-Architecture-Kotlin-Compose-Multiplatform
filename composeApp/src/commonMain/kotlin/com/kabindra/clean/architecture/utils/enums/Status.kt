package com.kabindra.clean.architecture.utils.enums

enum class Status(val status: String) {
    True("1"),
    False("0")
}

inline fun <reified T : Enum<T>> getStatus(status: String): Boolean {
    if (Status.True.status == status) {
        return true
    } else if (Status.False.status == status) {
        return false
    }

    return false
}