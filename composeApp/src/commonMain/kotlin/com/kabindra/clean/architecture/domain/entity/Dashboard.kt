package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class Dashboard(
    val response: DashboardInfo?
) : BaseResponse()

@Serializable
data class DashboardInfo(
    val data: List<DashboardData>? = listOf(),
    val unread_notification_count: Int? = 0,
)

@Serializable
data class DashboardData(
    val title: String? = "",
    val type: String? = "",
    val content: List<DashboardContent>? = listOf()
)

@Serializable
data class DashboardContent(
    val workflow: String? = "",
    val count: String? = "",
    val workflow_name: String? = "",
    val name: String? = "",
    val icon: String? = "",
    val type: String? = ""
)