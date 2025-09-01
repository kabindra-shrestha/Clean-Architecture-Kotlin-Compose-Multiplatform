package com.kabindra.clean.architecture.utils.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.ui.graphics.vector.ImageVector

enum class DashboardExpandableFabType(
    val title: String?,
    val icon: ImageVector?,
    val slug: String
) {
    TimeRequestApproval("Time Request", Icons.Default.CalendarToday, "time_request_approval"),
    LeaveApproval("Leave Request", Icons.Default.CalendarToday, "leave_approval")
}

inline fun <reified T : Enum<T>> getDashboardExpandableFabType(slug: String): DashboardExpandableFabType {
    return enumValues<T>().find { (it as DashboardExpandableFabType).slug == slug } as DashboardExpandableFabType
}