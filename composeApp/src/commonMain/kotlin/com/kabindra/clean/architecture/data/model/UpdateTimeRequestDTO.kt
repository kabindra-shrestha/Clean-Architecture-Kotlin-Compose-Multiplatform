package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.UpdateTimeRequest
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTimeRequestDTO(
    val response: String? = ""
) : BaseResponse()

fun UpdateTimeRequestDTO.toDomain(): UpdateTimeRequest {
    return UpdateTimeRequest(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}


