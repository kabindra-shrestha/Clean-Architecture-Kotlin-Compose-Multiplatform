package com.kabindra.clean.architecture.utils.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class TicketStateType(val title: String, val icon: ImageVector?, val backgroundColor: Color) {
    Approved("Approved", Icons.Default.DoneAll, Color.Red),
    Uploaded("Uploaded", Icons.Default.DoneAll, Color.Red),
    Endorsed("Endorsed", Icons.Default.DoneAll, Color.Red),
    Verified("Verified", Icons.Default.Check, Color.Red),
    Allocated("Allocated", Icons.Default.Check, Color.Red),
    Rejected("Rejected", Icons.Default.Close, Color.Red),
    Submitted("Submitted", Icons.Default.Check, Color.Red),
    SentForReview("SentForReview", Icons.Default.PanoramaFishEye, Color.Red),
    Reviewed("Reviewed", Icons.Default.VerifiedUser, Color.Red),
    ReviewRejected("ReviewRejected", Icons.Default.Close, Color.Red),
    Cancelled("Cancelled", Icons.Default.Delete, Color.Red),
    Escalated("Escalated", Icons.Default.ArrowUpward, Color.Red),
    Returned("Returned", Icons.Default.SubdirectoryArrowRight, Color.Red),
    Recalled("Recalled", Icons.Default.Cached, Color.Red),
    Reopened("Reopened", Icons.Default.Layers, Color.Red),
    Assigned("Assigned", Icons.Default.Check, Color.Red),
    Reverted("Reverted", Icons.AutoMirrored.Filled.Backspace, Color.Red),
    Edited("Edited", Icons.Default.Edit, Color.Red),
    Current("current", Icons.Default.HorizontalRule, Color.Red),
}

inline fun <reified T : Enum<T>> getTicketStateType(title: String): TicketStateType {
    return enumValues<T>().find { (it as TicketStateType).title == title } as TicketStateType
}