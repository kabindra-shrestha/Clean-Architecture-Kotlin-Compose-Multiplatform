package com.kabindra.clean.architecture.utils.enums

import androidx.compose.ui.graphics.Color
import com.kabindra.clean.architecture.presentation.ui.theme.leaveRequestLight
import com.kabindra.clean.architecture.presentation.ui.theme.timeRequestLight

enum class TicketWorkflowType(val title: String, val slug: String, val color: Color) {
    Everything("Everything", "all", Color.Transparent),
    TimeRequestApproval("Time Request Approval", "time_request_approval", timeRequestLight),
    LeaveApproval("Leave Approval", "leave_approval", leaveRequestLight)
}

inline fun <reified T : Enum<T>> getTicketWorkflowType(slug: String): TicketWorkflowType {
    return enumValues<T>().find { (it as TicketWorkflowType).slug == slug } as TicketWorkflowType
}


