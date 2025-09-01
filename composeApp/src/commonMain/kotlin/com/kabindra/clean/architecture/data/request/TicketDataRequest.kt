package com.kabindra.clean.architecture.data.request

data class TicketDataRequest(
    val ticketType: String,
    val workflow: String,
    val page: Int,
)