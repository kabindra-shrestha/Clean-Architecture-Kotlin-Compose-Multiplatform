package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class Attendance(
    val response: AttendanceInfo?,
) : BaseResponse()

@Serializable
data class AttendanceInfo(
    val attendance: List<AttendanceInfoData>? = listOf()
)

@Serializable
data class AttendanceInfoData(
    val date_en: String? = "",
    val date_np: String? = "",
    val duty_end: String? = "",
    val duty_start: String? = "",
    val id: String? = "",
    val in_time: String? = "",
    val out_time: String? = "",
    val remarks: String? = "",
    val source: String? = "",
    val status: String? = "",
    val total_hours: String? = "",
    val time_request: Boolean? = false,
    val leave_request: Boolean? = false
)