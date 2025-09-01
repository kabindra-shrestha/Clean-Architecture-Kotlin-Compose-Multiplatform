package com.kabindra.clean.architecture.data.request

data class TicketDocumentRemoveRequest(
    val ticket_id: String,
    val document_id: String,
)