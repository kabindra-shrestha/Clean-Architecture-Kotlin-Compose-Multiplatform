package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.TicketChangeOwner
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TicketChangeOwnerDTO(
    val response: String? = ""
) : BaseResponse()

fun TicketChangeOwnerDTO.toDomain(): TicketChangeOwner {
    return TicketChangeOwner(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

