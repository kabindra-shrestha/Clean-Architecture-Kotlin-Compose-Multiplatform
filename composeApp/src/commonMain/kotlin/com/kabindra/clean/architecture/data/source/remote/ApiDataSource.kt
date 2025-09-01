package com.kabindra.clean.architecture.data.source.remote

import com.kabindra.clean.architecture.data.request.ApplyLeaveRequestDataRequest
import com.kabindra.clean.architecture.data.request.ApplyTimeRequestDataRequest
import com.kabindra.clean.architecture.data.request.AttendanceDataRequest
import com.kabindra.clean.architecture.data.request.LeaveRequestUpdateDataRequest
import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.data.request.LoginSendOTPDataRequest
import com.kabindra.clean.architecture.data.request.MPINSetDataRequest
import com.kabindra.clean.architecture.data.request.MPINVerifyDataRequest
import com.kabindra.clean.architecture.data.request.NotificationMarkAsReadRequest
import com.kabindra.clean.architecture.data.request.RecordedTimeRequest
import com.kabindra.clean.architecture.data.request.RefreshTokenDataRequest
import com.kabindra.clean.architecture.data.request.TicketChangeOwnerRequest
import com.kabindra.clean.architecture.data.request.TicketChangeStateRequest
import com.kabindra.clean.architecture.data.request.TicketDataRequest
import com.kabindra.clean.architecture.data.request.TicketDetailsRequest
import com.kabindra.clean.architecture.data.request.TicketDocumentRemoveRequest
import com.kabindra.clean.architecture.data.request.TicketFilterDataRequest
import com.kabindra.clean.architecture.data.request.TimeRequestUpdateDataRequest
import io.ktor.client.statement.HttpResponse

class ApiDataSource(private val apiService: ApiService) {

    suspend fun getLoginCheckUser(loginCheckUserDataRequest: LoginCheckUserDataRequest): HttpResponse {
        return apiService.getLoginCheckUser(
            loginCheckUserDataRequest.username,
            loginCheckUserDataRequest.appLoginCode,
            loginCheckUserDataRequest.fcmToken
        )
    }

    suspend fun getLoginSendOTP(loginSendOTPDataRequest: LoginSendOTPDataRequest): HttpResponse {
        return apiService.getLoginSendOTP(
            loginSendOTPDataRequest.username,
            loginSendOTPDataRequest.appLoginCode,
            loginSendOTPDataRequest.otp,
            loginSendOTPDataRequest.fcmToken
        )
    }

    suspend fun getLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest): HttpResponse {
        return apiService.getLoginRefreshUserDetails(
            loginRefreshUserDetailsDataRequest.fcmToken
        )
    }

    suspend fun getRefreshToken(refreshTokenDataRequest: RefreshTokenDataRequest): HttpResponse {
        return apiService.getRefreshToken(
            refreshTokenDataRequest.refreshToken
        )
    }

    suspend fun getDashboard(): HttpResponse {
        return apiService.getDashboard()
    }

    suspend fun getProfile(): HttpResponse {
        return apiService.getProfile()
    }

    suspend fun getMPINSet(mPINSetDataRequest: MPINSetDataRequest): HttpResponse {
        return apiService.getMPINSet(
            mPINSetDataRequest.mpin
        )
    }

    suspend fun getMPINVerify(mPINVerifyDataRequest: MPINVerifyDataRequest): HttpResponse {
        return apiService.getMPINVerify(
            mPINVerifyDataRequest.mpin
        )
    }

    suspend fun getAttendance(attendanceDataRequest: AttendanceDataRequest): HttpResponse {
        return apiService.getAttendance(
            attendanceDataRequest.year,
            attendanceDataRequest.month
        )
    }

    suspend fun getAttendanceFilter(): HttpResponse {
        return apiService.getAttendanceFilter()
    }

    suspend fun getTimeRequestVerifiers(): HttpResponse {
        return apiService.getTimeRequestVerifiers()
    }

    suspend fun getRecordedTime(recordedTimeRequest: RecordedTimeRequest): HttpResponse {
        return apiService.getRecordedTime(recordedTimeRequest.date)
    }

    suspend fun getApplyTimeRequest(applyTimeRequestDataRequest: ApplyTimeRequestDataRequest): HttpResponse {
        return apiService.getApplyTimeRequest(
            applyTimeRequestDataRequest.nep_date,
            applyTimeRequestDataRequest.actual_in_time,
            applyTimeRequestDataRequest.actual_in_time_remarks,
            applyTimeRequestDataRequest.actual_out_time,
            applyTimeRequestDataRequest.actual_out_time_remarks,
            applyTimeRequestDataRequest.verifier_id,
            applyTimeRequestDataRequest.documents
        )
    }


    suspend fun getTimeRequestEdit(ticket_id: String): HttpResponse {
        return apiService.getTimeRequestEdit(ticket_id)
    }

    suspend fun getTimeRequestUpdate(timeRequestUpdateDataRequest: TimeRequestUpdateDataRequest): HttpResponse {
        return apiService.getTimeRequestUpdate(
            timeRequestUpdateDataRequest.ticket_id,
            timeRequestUpdateDataRequest.nep_date,
            timeRequestUpdateDataRequest.actual_in_time,
            timeRequestUpdateDataRequest.actual_in_time_remarks,
            timeRequestUpdateDataRequest.actual_out_time,
            timeRequestUpdateDataRequest.actual_out_time_remarks,
            timeRequestUpdateDataRequest.verifier_id,
            timeRequestUpdateDataRequest.documents
        )

    }

    suspend fun getLeaveRequestVerifiers(): HttpResponse {
        return apiService.getLeaveRequestVerifiers()
    }

    suspend fun getLeaveDetails(): HttpResponse {
        return apiService.getLeaveDetails()
    }

    suspend fun getApplyLeaveRequest(applyTimeRequestDataRequest: ApplyLeaveRequestDataRequest): HttpResponse {
        return apiService.getApplyLeaveRequest(
            applyTimeRequestDataRequest.leave_type_id,
            applyTimeRequestDataRequest.leave_option_id,
            applyTimeRequestDataRequest.nep_start_date,
            applyTimeRequestDataRequest.nep_end_date,
            applyTimeRequestDataRequest.verifier_id,
            applyTimeRequestDataRequest.documents,
            applyTimeRequestDataRequest.remarks
        )
    }

    suspend fun getLeaveRequestEdit(ticket_id: String): HttpResponse {
        return apiService.getLeaveRequestEdit(ticket_id)
    }

    suspend fun getLeaveRequestUpdate(applyTimeRequestUpdateDataRequest: LeaveRequestUpdateDataRequest): HttpResponse {
        return apiService.getLeaveRequestUpdate(
            applyTimeRequestUpdateDataRequest.ticket_id,
            applyTimeRequestUpdateDataRequest.leave_type_id,
            applyTimeRequestUpdateDataRequest.leave_option_id,
            applyTimeRequestUpdateDataRequest.nep_start_date,
            applyTimeRequestUpdateDataRequest.nep_end_date,
            applyTimeRequestUpdateDataRequest.verifier_id,
            applyTimeRequestUpdateDataRequest.documents,
            applyTimeRequestUpdateDataRequest.remarks
        )
    }

    suspend fun getDocumentRemove(ticketDocumentRemoveRequest: TicketDocumentRemoveRequest): HttpResponse {
        return apiService.getDocumentRemove(
            ticketDocumentRemoveRequest.ticket_id,
            ticketDocumentRemoveRequest.document_id
        )
    }

    suspend fun getTicket(ticketDataRequest: TicketDataRequest): HttpResponse {
        return apiService.getTicket(
            ticketDataRequest.ticketType,
            ticketDataRequest.workflow,
            ticketDataRequest.page
        )
    }

    suspend fun getTicketFilter(ticketFilterDataRequest: TicketFilterDataRequest): HttpResponse {
        return apiService.getTicketFilter(ticketFilterDataRequest.ticketType)
    }

    suspend fun getTicketDetails(ticketDetailsRequest: TicketDetailsRequest): HttpResponse {
        return apiService.getTicketDetails(ticketDetailsRequest.ticket_id)
    }

    suspend fun getTicketChangeState(ticketChangeStateRequest: TicketChangeStateRequest): HttpResponse {
        return apiService.getTicketChangeState(
            ticketChangeStateRequest.ticket_id,
            ticketChangeStateRequest.state,
            ticketChangeStateRequest.comment,
            ticketChangeStateRequest.next_owner_id
        )
    }

    suspend fun getTicketChangeOwner(ticketChangeOwnerRequest: TicketChangeOwnerRequest): HttpResponse {
        return apiService.getTicketChangeOwner(
            ticketChangeOwnerRequest.ticket_id,
            ticketChangeOwnerRequest.next_owner_id
        )
    }

    suspend fun getNotification(): HttpResponse {
        return apiService.getNotification()
    }

    suspend fun getNotificationMarkAsAllRead(): HttpResponse {
        return apiService.getNotificationMarkAsAllRead()
    }

    suspend fun getNotificationMarkAsRead(notificationMarkAsReadRequest: NotificationMarkAsReadRequest): HttpResponse {
        return apiService.getNotificationMarkAsRead(
            notificationMarkAsReadRequest.id
        )
    }

    suspend fun getLogout(): HttpResponse {
        return apiService.getLogout()
    }


}