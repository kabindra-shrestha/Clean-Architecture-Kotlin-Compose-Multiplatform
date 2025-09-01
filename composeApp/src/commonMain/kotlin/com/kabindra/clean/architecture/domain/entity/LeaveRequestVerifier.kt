package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LeaveRequestVerifier(
    val response: LeaveRequestVerifierInfo?,
) : BaseResponse()

@Serializable
data class LeaveRequestVerifierInfo(
    val verifiers: List<LeaveRequestVerifierData>? = listOf()
)

@Serializable
data class LeaveRequestVerifierData(
    val id: Int? = 0,
    val name: String? = "",
)