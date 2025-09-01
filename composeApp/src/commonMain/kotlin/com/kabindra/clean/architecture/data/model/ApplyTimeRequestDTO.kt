package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.ApplyTime
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class ApplyTimeRequestDTO(
    val response: String? = ""
) : BaseResponse()

fun ApplyTimeRequestDTO.toDomain(): ApplyTime {
    return ApplyTime(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}


