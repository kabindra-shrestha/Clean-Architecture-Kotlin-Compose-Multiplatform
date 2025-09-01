package com.kabindra.clean.architecture.data.source.remote

object ApiEndpoints {

    const val API_LOGIN_CHECK_USER = "api/check-user"
    const val API_LOGIN_VERIFY_OTP = "api/verify-otp"
    const val API_LOGIN_REFRESH_USER_DETAILS = "api/refresh-user-details"
    const val API_REFRESH_TOKEN = "/api/refresh"
    const val API_MPIN_SET = "/api/set-mpin"
    const val API_MPIN_VERIFY = "/api/verify-mpin"
    const val API_DASHBOARD = "/api/dashboard"
    const val API_PROFILE = "/api/profile"
    const val API_ATTENDANCE = "/api/my-attendance"
    const val API_ATTENDANCE_FILTER = "/api/filter"

    const val API_TIME_REQUEST_VERIFIERS = "/api/time-request/verifiers"
    const val API_APPLY_TIME_REQUEST = "/api/time-request/apply"
    fun API_TIME_REQUEST_EDIT(ticket_id: String) = "/api/time-request/$ticket_id/detail"
    fun API_TIME_REQUEST_UPDATE(ticket_id: String) = "/api/time-request/$ticket_id/update"

    const val API_LEAVE_REQUEST_VERIFIERS = "/api/leave-request/verifiers"
    const val API_APPLY_LEAVE_REQUEST = "/api/leave-request/apply"
    const val API_LEAVE_DETAILS = "/api/leave-request/leave-details"
    fun API_LEAVE_DETAILS_EDIT(ticket_id: String) = "/api/leave-request/$ticket_id/detail"
    fun API_LEAVE_DETAILS_UPDATE(ticket_id: String) = "/api/leave-request/$ticket_id/update"

    const val API_REMOVE_DOCUMENT = "/api/tickets/remove-document"

    const val API_RECORDED_TIME = "/api/time-request/recorded-time"

    const val API_TICKET = "/api/tickets"
    const val API_TICKET_FILTER = "/api/tickets/workflows"
    fun API_TICKET_DETAILS(ticket_id: String) = "/api/tickets/$ticket_id/details"
    const val API_TICKET_CHANGE_STATE = "/api/tickets/change-state"
    const val API_TICKET_CHANGE_OWNER = "/api/tickets/change-owner"

    const val API_NOTIFICATION = "/api/notification"
    const val API_NOTIFICATION_MARK_AS_ALL_READ = "/api/notification/mark-all-as-read"
    const val API_NOTIFICATION_MARK_AS_READ = "/api/notification/mark-as-read"

    const val API_LOGOUT = "/api/logout"

}

