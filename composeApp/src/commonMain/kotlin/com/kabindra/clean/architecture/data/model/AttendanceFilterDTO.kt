package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.AttendanceFilter
import com.kabindra.clean.architecture.domain.entity.AttendanceFilterInfo
import com.kabindra.clean.architecture.domain.entity.Department
import com.kabindra.clean.architecture.domain.entity.FiscalYear
import com.kabindra.clean.architecture.domain.entity.LeaveType
import com.kabindra.clean.architecture.domain.entity.Month
import com.kabindra.clean.architecture.domain.entity.StateList
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceFilterDTO(
    val response: AttendanceFilterInfoDTO?
) : BaseResponse()

@Serializable
data class AttendanceFilterInfoDTO(
    val departmentList: List<DepartmentDTO>? = listOf(),
    val fiscalYearList: List<FiscalYearDTO>? = listOf(),
    val leaveTypeList: List<LeaveTypeDTO>? = listOf(),
    val monthList: List<MonthDTO>? = listOf(),
    val yearList: List<Int>? = listOf(),
    val stateList: StateListDTO
)

@Serializable
data class DepartmentDTO(
    val id: Int? = 0,
    val name: String? = ""
)

@Serializable
data class FiscalYearDTO(
    val id: Int? = 0,
    val name: String? = ""
)

@Serializable
data class LeaveTypeDTO(
    val id: Int? = 0,
    val name: String? = ""
)

@Serializable
data class MonthDTO(
    val id: Int? = 0,
    val name: String? = ""
)

@Serializable
data class StateListDTO(
    val Allocated: String? = "",
    val Approved: String? = "",
    val Assigned: String? = "",
    val Rejected: String? = "",
    val Reverted: String? = "",
    val Submitted: String? = "",
    val Verified: String? = ""
)

// Mapper function
fun AttendanceFilterDTO.toDomain(): AttendanceFilter {
    return AttendanceFilter(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun AttendanceFilterInfoDTO.toDomain(): AttendanceFilterInfo {
    return AttendanceFilterInfo(
        departmentList = departmentList?.map { it.toDomain() },
        fiscalYearList = fiscalYearList?.map { it.toDomain() },
        leaveTypeList = leaveTypeList?.map { it.toDomain() },
        monthList = monthList?.map { it.toDomain() },
        yearList = yearList?.map { it },
        stateList = stateList.toDomain(),

        )
}

fun DepartmentDTO.toDomain(): Department {
    return Department(
        id = id,
        name = name,
    )
}

fun FiscalYearDTO.toDomain(): FiscalYear {
    return FiscalYear(
        id = id,
        name = name,
    )
}

fun LeaveTypeDTO.toDomain(): LeaveType {
    return LeaveType(
        id = id,
        name = name,
    )
}

fun MonthDTO.toDomain(): Month {
    return Month(
        id = id,
        name = name,
    )
}

fun StateListDTO.toDomain(): StateList {
    return StateList(
        Allocated = Allocated,
        Approved = Approved,
        Assigned = Assigned,
        Rejected = Rejected,
        Reverted = Reverted,
        Submitted = Submitted,
        Verified = Verified,
    )
}






