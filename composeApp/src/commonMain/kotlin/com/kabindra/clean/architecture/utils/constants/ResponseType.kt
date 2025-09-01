package com.kabindra.clean.architecture.utils.constants

sealed class ResponseType {
    data object None : ResponseType()
    data object VersionCheckUpdate : ResponseType()
    data object LoginCheckUser : ResponseType()
    data object LoginVerifyOTP : ResponseType()
    data object Logout : ResponseType()
    data object MPINSet : ResponseType()
    data object MPINVerify : ResponseType()
    data object Refresh : ResponseType()
    data object Dashboard : ResponseType()
    data object Attendance : ResponseType()
    data object ApplyLeaveRequest : ResponseType()
    data object ApplyTimeRequest : ResponseType()
    data object UpdateTimeRequest : ResponseType()
    data object UpdateLeaveRequest : ResponseType()
    data object TicketChangeState : ResponseType()
    data object NotificationMarkAsRead : ResponseType()
    data object NotificationMarkAllAsRead : ResponseType()
    data object DocumentRemove : ResponseType()

}