package com.kabindra.clean.architecture.data.request

data class TicketChangeOwnerRequest(
    val ticket_id: String,
    val next_owner_id: String
)