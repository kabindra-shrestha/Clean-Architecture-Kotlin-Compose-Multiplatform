package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.AssignedLeaveType
import com.kabindra.clean.architecture.domain.entity.AssignedLeaveTypeData
import com.kabindra.clean.architecture.domain.entity.AssignedLeaveTypeInfo
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class AssignedLeaveTypeDTO(
    val response: AssignedLeaveTypeInfoDTO?
) : BaseResponse()

@Serializable
data class AssignedLeaveTypeInfoDTO(
    val assigned_leaves: List<AssignedLeaveTypeDataDTO>? = listOf(),
)

@Serializable
data class AssignedLeaveTypeDataDTO(
    val assigned_leave: String? = "",
    val id: Int? = 0,
    val leave_taken: Double? = 0.0,
    val name: String? = ""
)

// Mapper function
fun AssignedLeaveTypeDTO.toDomain(): AssignedLeaveType {
    return AssignedLeaveType(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun AssignedLeaveTypeInfoDTO.toDomain(): AssignedLeaveTypeInfo {
    return AssignedLeaveTypeInfo(
        assigned_leaves = assigned_leaves!!.map { it.toDomain() },
    )
}

fun AssignedLeaveTypeDataDTO.toDomain(): AssignedLeaveTypeData {
    return AssignedLeaveTypeData(
        assigned_leave = assigned_leave,
        id = id,
        leave_taken = leave_taken,
        name = name,
    )
}
