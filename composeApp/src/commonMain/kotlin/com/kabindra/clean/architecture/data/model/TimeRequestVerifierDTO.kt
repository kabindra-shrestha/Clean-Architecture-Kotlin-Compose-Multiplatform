package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifier
import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifierData
import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifierInfo
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TimeRequestVerifierDTO(
    val response: TimeRequestVerifierInfoDTO?
) : BaseResponse()

@Serializable
data class TimeRequestVerifierInfoDTO(
    val verifiers: List<TimeRequestVerifierDataDTO>? = listOf()
)

@Serializable
data class TimeRequestVerifierDataDTO(
    val id: Int? = 0,
    val name: String? = "",
)

fun TimeRequestVerifierDTO.toDomain(): TimeRequestVerifier {
    return TimeRequestVerifier(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun TimeRequestVerifierInfoDTO.toDomain(): TimeRequestVerifierInfo {
    return TimeRequestVerifierInfo(
        verifiers = verifiers?.map { it.toDomain() })
}

fun TimeRequestVerifierDataDTO.toDomain(): TimeRequestVerifierData {
    return TimeRequestVerifierData(
        id = id,
        name = name,
    )
}

