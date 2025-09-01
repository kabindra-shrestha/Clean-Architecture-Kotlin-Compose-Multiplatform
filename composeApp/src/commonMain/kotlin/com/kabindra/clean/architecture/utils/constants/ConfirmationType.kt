package com.kabindra.clean.architecture.utils.constants

sealed class ConfirmationType {
    data object None : ConfirmationType()
    data object Logout : ConfirmationType()
    data object NotificationMarkRead : ConfirmationType()
    data object NotificationMarkAllAsRead : ConfirmationType()
    data object TimeRequest : ConfirmationType()
    data object LeaveRequest : ConfirmationType()
    data object TicketEdit : ConfirmationType()
    data object TicketApprove : ConfirmationType()
    data object TicketCancel : ConfirmationType()
    data object TicketVerify : ConfirmationType()
    data object TicketRevert : ConfirmationType()
    data object TicketChangeState : ConfirmationType()
    data object EditLeaveRequestDocumentRemoval : ConfirmationType()
    data object EditTimeRequestDocumentRemoval : ConfirmationType()
}