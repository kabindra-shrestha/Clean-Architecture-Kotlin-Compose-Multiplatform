package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.RecordedTime
import com.kabindra.clean.architecture.domain.entity.RecordedTimeInfo
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class RecordedTimeDTO(
    val response: RecordedTimeInfoDTO?
) : BaseResponse()

@Serializable
data class RecordedTimeInfoDTO(
    val check_in: String? = "",
    val check_out: String? = ""
)

fun RecordedTimeDTO.toDomain(): RecordedTime {
    return RecordedTime(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun RecordedTimeInfoDTO.toDomain(): RecordedTimeInfo {
    return RecordedTimeInfo(
        check_in = check_in,
        check_out = check_out,
    )
}
