package com.kabindra.clean.architecture.utils.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material.icons.filled.OnDeviceTraining
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class ProfileContentType(val title: String, val slug: String, val icon: ImageVector?) {
    Profile("Profile", "profile", Icons.Default.Person),
    Documents("Documents", "documents", Icons.Default.DocumentScanner),
    Training("Training", "training", Icons.Default.OnDeviceTraining),
    Misc("Misc", "misc", Icons.Default.MiscellaneousServices),
    SalarySlip("Salary Slip", "salary-slip", Icons.Default.Payment),
    PaySlip("Pay Slip", "pay-slip", Icons.Default.Payment),
    Approver("Approver", "approver", Icons.Default.Person)
}

inline fun <reified T : Enum<T>> getProfileContentType(slug: String): ProfileContentType {
    return enumValues<T>().find { (it as ProfileContentType).slug == slug } as ProfileContentType
}