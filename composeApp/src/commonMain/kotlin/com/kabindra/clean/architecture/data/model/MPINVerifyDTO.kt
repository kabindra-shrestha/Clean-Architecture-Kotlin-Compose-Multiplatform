package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.MPINVerify
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class MPINVerifyDTO(
    val response: String? = ""
) : BaseResponse()

// Mapper function
fun MPINVerifyDTO.toDomain(): MPINVerify {
    return MPINVerify(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}