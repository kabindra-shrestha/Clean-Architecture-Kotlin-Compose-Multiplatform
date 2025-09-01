package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.Dashboard
import com.kabindra.clean.architecture.domain.entity.DashboardContent
import com.kabindra.clean.architecture.domain.entity.DashboardData
import com.kabindra.clean.architecture.domain.entity.DashboardInfo
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class DashboardDTO(
    val response: DashboardInfoDTO?
) : BaseResponse()

@Serializable
data class DashboardInfoDTO(
    val data: List<DashboardDataDTO>? = listOf(),
    val unread_notification_count: Int? = 0,
)

@Serializable
data class DashboardDataDTO(
    val title: String? = "",
    val type: String? = "",
    val content: List<DashboardContentDTO>? = listOf()
)

@Serializable
data class DashboardContentDTO(
    val workflow: String? = "",
    val count: String? = "",
    val workflow_name: String? = "",
    val name: String? = "",
    val icon: String? = "",
    val type: String? = ""
)

// Mapper function
fun DashboardDTO.toDomain(): Dashboard {
    return Dashboard(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun DashboardInfoDTO.toDomain(): DashboardInfo {
    return DashboardInfo(
        data = data?.map { it.toDomain() },
        unread_notification_count = unread_notification_count
    )
}

fun DashboardDataDTO.toDomain(): DashboardData {
    return DashboardData(
        title = title,
        type = type,
        content = content?.map { it.toDomain() },
    )
}

fun DashboardContentDTO.toDomain(): DashboardContent {
    return DashboardContent(
        workflow = workflow,
        count = count,
        workflow_name = workflow_name,
        name = name,
        icon = icon,
        type = type,
    )
}





