package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.Attendance
import com.kabindra.clean.architecture.domain.entity.AttendanceInfo
import com.kabindra.clean.architecture.domain.entity.AttendanceInfoData
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceDTO(
    val response: AttendanceInfoDTO?
) : BaseResponse()

@Serializable
data class AttendanceInfoDTO(
    val attendance: List<AttendanceInfoDataDTO>? = listOf()
)

@Serializable
data class AttendanceInfoDataDTO(
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

// Mapper function
fun AttendanceDTO.toDomain(): Attendance {
    return Attendance(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun AttendanceInfoDTO.toDomain(): AttendanceInfo {
    return AttendanceInfo(
        attendance = attendance?.map { it.toDomain() })
}

fun AttendanceInfoDataDTO.toDomain(): AttendanceInfoData {
    return AttendanceInfoData(
        date_en = date_en,
        date_np = date_np,
        duty_end = duty_end,
        duty_start = duty_start,
        id = id,
        in_time = in_time,
        out_time = out_time,
        remarks = remarks,
        source = source,
        status = status,
        total_hours = total_hours,
        time_request = time_request,
        leave_request = leave_request,
    )
}






