package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.TicketChangeState
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TicketChangeStateDTO(
    val response: String? = ""
) : BaseResponse()

fun TicketChangeStateDTO.toDomain(): TicketChangeState {
    return TicketChangeState(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

