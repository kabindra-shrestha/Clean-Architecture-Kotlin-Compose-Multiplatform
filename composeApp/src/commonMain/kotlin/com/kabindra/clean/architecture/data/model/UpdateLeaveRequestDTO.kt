package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.UpdateLeaveRequest
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class UpdateLeaveRequestDTO(
    val response: String? = ""
) : BaseResponse()

fun UpdateLeaveRequestDTO.toDomain(): UpdateLeaveRequest {
    return UpdateLeaveRequest(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}


