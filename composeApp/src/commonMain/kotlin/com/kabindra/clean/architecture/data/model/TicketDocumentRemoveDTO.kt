package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.TicketDocumentRemove
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TicketDocumentRemoveDTO(
    val response: String? = ""
) : BaseResponse()

fun TicketDocumentRemoveDTO.toDomain(): TicketDocumentRemove {
    return TicketDocumentRemove(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}


