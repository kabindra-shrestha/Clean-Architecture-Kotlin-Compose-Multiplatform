package com.kabindra.clean.architecture.data.request

data class TicketChangeStateRequest(
    val ticket_id: String,
    val state: String,
    val comment: String,
    val next_owner_id: String,
)