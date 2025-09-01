package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.LeaveDetails
import com.kabindra.clean.architecture.domain.entity.LeaveDetailsInfo
import com.kabindra.clean.architecture.domain.entity.LeaveOption
import com.kabindra.clean.architecture.domain.entity.LeaveTypes
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LeaveDetailsDTO(
    val response: LeaveDetailsInfoDTO?
) : BaseResponse()

@Serializable
data class LeaveDetailsInfoDTO(
    val leave_types: List<LeaveTypesDTO>? = listOf(),
    val default_leave_options: List<LeaveOptionDTO>? = listOf()
)

@Serializable
data class LeaveTypesDTO(
    val id: Int? = 0,
    val leave_count_message: String? = "",
    val leave_options: List<LeaveOptionDTO>? = listOf(),
    val name: String? = ""
)

@Serializable
data class LeaveOptionDTO(
    val id: Int? = 0,
    val name: String? = ""
)

// Mapper function
fun LeaveDetailsDTO.toDomain(): LeaveDetails {
    return LeaveDetails(
        response = response!!.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun LeaveDetailsInfoDTO.toDomain(): LeaveDetailsInfo {
    return LeaveDetailsInfo(
        leave_types = leave_types!!.map { it.toDomain() },
        default_leave_options = default_leave_options!!.map { it.toDomain() },
    )
}

fun LeaveTypesDTO.toDomain(): LeaveTypes {
    return LeaveTypes(
        id = id,
        name = name,
        leave_count_message = leave_count_message,
        leave_options = leave_options!!.map { it.toDomain() },
    )
}

fun LeaveOptionDTO.toDomain(): LeaveOption {
    return LeaveOption(
        id = id,
        name = name,
    )
}
