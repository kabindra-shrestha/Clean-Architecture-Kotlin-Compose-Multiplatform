package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceFilter(
    val response: AttendanceFilterInfo?
) : BaseResponse()

@Serializable
data class AttendanceFilterInfo(
    val departmentList: List<Department>? = listOf(),
    val fiscalYearList: List<FiscalYear>? = listOf(),
    val leaveTypeList: List<LeaveType>? = listOf(),
    val monthList: List<Month>? = listOf(),
    val yearList: List<Int>? = listOf(),
    val stateList: StateList
)

@Serializable
data class Department(
    val id: Int? = 0,
    val name: String? = ""
)

@Serializable
data class FiscalYear(
    val id: Int? = 0,
    val name: String? = ""
)

@Serializable
data class LeaveType(
    val id: Int? = 0,
    val name: String? = ""
)

@Serializable
data class Month(
    val id: Int? = 0,
    val name: String? = ""
)

@Serializable
data class StateList(
    val Allocated: String? = "",
    val Approved: String? = "",
    val Assigned: String? = "",
    val Rejected: String? = "",
    val Reverted: String? = "",
    val Submitted: String? = "",
    val Verified: String? = ""
)
