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
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector

enum class TicketActionType(val title: String, val slug: String, val icon: ImageVector?) {
    Approve("Approve", "approve", Icons.Default.DoneAll),
    Verify("Verify", "verify", Icons.Default.Check),
    ChangeOwner("Change Owner", "change_owner", Icons.Default.SwitchAccount),
    Edit("Edit", "edit", Icons.Default.Edit),
    Reject("Reject", "reject", Icons.Default.Close),
    Cancel("Cancel", "cancel", Icons.Default.Delete),
    Revert("Revert", "revert", Icons.AutoMirrored.Filled.Backspace),
    Escalate("Escalate", "escalate", Icons.Default.ArrowUpward),
    Recall("Recall", "recall", Icons.Default.Cached),
    Reopen("Reopen", "reopen", Icons.Default.Layers),
    Return("Return", "return", Icons.Default.SubdirectoryArrowRight),
    Review("Review", "review", Icons.Default.VerifiedUser),
    SendForReview("Send For Review", "send_for_review", Icons.Default.PanoramaFishEye)
}

inline fun <reified T : Enum<T>> getTicketActionType(slug: String): TicketActionType {
    return enumValues<T>().find { (it as TicketActionType).slug == slug } as TicketActionType
}