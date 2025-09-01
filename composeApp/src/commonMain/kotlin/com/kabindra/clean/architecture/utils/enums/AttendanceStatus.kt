package com.kabindra.clean.architecture.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kabindra.clean.architecture.presentation.ui.theme.absent
import com.kabindra.clean.architecture.presentation.ui.theme.dayOff
import com.kabindra.clean.architecture.presentation.ui.theme.missedPunch
import com.kabindra.clean.architecture.presentation.ui.theme.present
import com.kabindra.clean.architecture.presentation.ui.theme.workOnDayOff

enum class AttendanceStatus(val attendanceStatus: String) {
    Present("Present"),
    Absent("Absent"),
    MissedPunch("Missed Punch"),
    DayOff("Day Off"),
    WorkOnDayOff("Work On Day Off");

    @Composable
    fun getColor(): Color {
        return when (this) {
            Present -> present()
            Absent -> absent()
            MissedPunch -> missedPunch()
            DayOff -> dayOff()
            WorkOnDayOff -> workOnDayOff()
        }
    }
}




