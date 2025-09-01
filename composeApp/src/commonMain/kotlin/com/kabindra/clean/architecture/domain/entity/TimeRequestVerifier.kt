package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TimeRequestVerifier(
    val response: TimeRequestVerifierInfo?,
) : BaseResponse()

@Serializable
data class TimeRequestVerifierInfo(
    val verifiers: List<TimeRequestVerifierData>? = listOf()
)

@Serializable
data class TimeRequestVerifierData(
    val id: Int? = 0,
    val name: String? = "",
)