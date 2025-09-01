package com.kabindra.clean.architecture.utils.enums

enum class TicketRemarkType(val type: String) {
    Required("required"),
    NotRequired("not-required"),
    Optional("optional")
}

inline fun <reified T : Enum<T>> getTicketRemarkType(type: String): TicketRemarkType {
    return enumValues<T>().find { (it as TicketRemarkType).type == type } as TicketRemarkType
}