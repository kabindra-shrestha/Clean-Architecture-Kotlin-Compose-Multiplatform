package com.kabindra.clean.architecture.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kabindra.clean.architecture.presentation.ui.theme.leaveRequest
import com.kabindra.clean.architecture.presentation.ui.theme.timeRequest

enum class TicketDetailsTitle(val detailTitle: String) {

    TimeRequest("Time Request"),
    LeaveRequest("Leave Request");

    @Composable
    fun getColor(): Color {
        return when (this) {
            TimeRequest -> timeRequest()
            LeaveRequest -> leaveRequest()
        }
    }
}




