package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.ApplyLeave
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class ApplyLeaveRequestDTO(
    val response: String? = ""
) : BaseResponse()

fun ApplyLeaveRequestDTO.toDomain(): ApplyLeave {
    return ApplyLeave(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}


