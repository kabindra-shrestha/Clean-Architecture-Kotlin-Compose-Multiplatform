package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LeaveDetails(
    val response: LeaveDetailsInfo?,
) : BaseResponse()

@Serializable
data class LeaveDetailsInfo(
    val leave_types: List<LeaveTypes>? = listOf(),
    val default_leave_options: List<LeaveOption>? = listOf()
)

@Serializable
data class LeaveTypes(
    val id: Int? = 0,
    val name: String? = "",
    val leave_count_message: String? = "",
    val leave_options: List<LeaveOption>? = listOf()
)


@Serializable
data class LeaveOption(
    val id: Int? = 0,
    val name: String? = ""
)