package com.kabindra.clean.architecture.utils.enums

enum class TicketContentType(val title: String, val slug: String) {
    ToBeReviewed("To Be Reviewed", "toBeReviewedRequests"),
    MyRequests("My Requests", "myRequests"),
    MyRequestsHistory("My Requests History", "myRequestsHistory"),
    ReviewedRequests("Reviewed Requests", "reviewedRequests")
}

inline fun <reified T : Enum<T>> getTicketContentType(slug: String): TicketContentType {
    return enumValues<T>().find { (it as TicketContentType).slug == slug } as TicketContentType
}

inline fun <reified T : Enum<T>> getTicketContentTypeIndex(slug: String): Int? {
    return enumValues<T>().indexOfFirst { (it as TicketContentType).slug == slug }
        .takeIf { it != -1 }
}