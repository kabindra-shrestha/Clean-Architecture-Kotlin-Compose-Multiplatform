package com.kabindra.clean.architecture.utils.enums

enum class HomeContentType(val title: String, val slug: String) {
    PendingTickets("Pending Tickets", "pending_tickets"),
    Tickets("My Tickets", "ticket"),
    Menu("Menus", "menu"),
}

inline fun <reified T : Enum<T>> getHomeContentType(slug: String): HomeContentType {
    return enumValues<T>().find { (it as HomeContentType).slug == slug } as HomeContentType
}