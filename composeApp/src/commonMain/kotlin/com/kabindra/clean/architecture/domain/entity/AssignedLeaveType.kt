package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class AssignedLeaveType(
    val response: AssignedLeaveTypeInfo?
) : BaseResponse()

@Serializable
data class AssignedLeaveTypeInfo(
    val assigned_leaves: List<AssignedLeaveTypeData>? = listOf()
)

@Serializable
data class AssignedLeaveTypeData(
    val assigned_leave: String? = "",
    val id: Int? = 0,
    val leave_taken: Double? = 0.0,
    val name: String? = ""
)


