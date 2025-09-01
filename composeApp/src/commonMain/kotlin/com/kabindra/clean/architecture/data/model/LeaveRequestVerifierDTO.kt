package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifier
import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifierData
import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifierInfo
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LeaveRequestVerifierDTO(
    val response: LeaveRequestVerifierInfoDTO?
) : BaseResponse()

@Serializable
data class LeaveRequestVerifierInfoDTO(
    val verifiers: List<LeaveRequestVerifierDataDTO>? = listOf()
)

@Serializable
data class LeaveRequestVerifierDataDTO(
    val id: Int? = 0,
    val name: String? = "",
)

fun LeaveRequestVerifierDTO.toDomain(): LeaveRequestVerifier {
    return LeaveRequestVerifier(
        response = response!!.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun LeaveRequestVerifierInfoDTO.toDomain(): LeaveRequestVerifierInfo {
    return LeaveRequestVerifierInfo(
        verifiers = verifiers?.map { it.toDomain() })
}

fun LeaveRequestVerifierDataDTO.toDomain(): LeaveRequestVerifierData {
    return LeaveRequestVerifierData(
        id = id,
        name = name,
    )
}

