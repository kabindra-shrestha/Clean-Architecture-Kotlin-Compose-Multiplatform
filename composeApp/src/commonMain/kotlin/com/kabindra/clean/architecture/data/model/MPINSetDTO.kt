package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.MPINSet
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class MPINSetDTO(
    val response: String? = ""
) : BaseResponse()

// Mapper function
fun MPINSetDTO.toDomain(): MPINSet {
    return MPINSet(
        response = response
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}