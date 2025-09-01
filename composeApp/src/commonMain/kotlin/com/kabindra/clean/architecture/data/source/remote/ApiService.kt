package com.kabindra.clean.architecture.data.source.remote

import com.kabindra.clean.architecture.utils.enums.FileExtensionType
import com.kabindra.clean.architecture.utils.enums.getFileExtensionType
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.runBlocking

class ApiService(private val client: HttpClient) {

    suspend fun getLoginCheckUser(
        username: String,
        appLoginCode: String,
        fcmToken: String
    ): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_LOGIN_CHECK_USER,
            formParameters = parameters {
                append("username", username)
                append("app_login_code", appLoginCode)
                append("fcm_token", fcmToken)
            }
        )
    }

    suspend fun getLoginSendOTP(
        username: String,
        appLoginCode: String,
        otp: String,
        fcmToken: String
    ): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_LOGIN_VERIFY_OTP,
            formParameters = parameters {
                append("username", username)
                append("app_login_code", appLoginCode)
                append("otp", otp)
                append("fcm_token", fcmToken)
            }
        )
    }

    suspend fun getLoginRefreshUserDetails(fcmToken: String): HttpResponse {
        return client.get(ApiEndpoints.API_LOGIN_REFRESH_USER_DETAILS) {
            url {
                parameters.append("fcm_token", fcmToken)
            }
        }
    }

    suspend fun getRefreshToken(refreshToken: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_REFRESH_TOKEN,
            formParameters = parameters {
                append("refresh_token", refreshToken)
            }
        ) {
            // This cannot be called here as it depend on Auth module of Ktor
            // That's why this API is used on Auth module
            // markAsRefreshTokenRequest()
        }
    }

    suspend fun getMPINSet(mpin: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_MPIN_SET,
            formParameters = parameters {
                append("mpin", mpin)
            }
        )
    }

    suspend fun getMPINVerify(mpin: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_MPIN_VERIFY,
            formParameters = parameters {
                append("mpin", mpin)
            }
        )
    }

    suspend fun getDashboard(): HttpResponse {
        return client.get(ApiEndpoints.API_DASHBOARD)
    }

    suspend fun getProfile(): HttpResponse {
        return client.submitForm(ApiEndpoints.API_PROFILE)
    }

    suspend fun getAttendance(year: String, month: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_ATTENDANCE,
            formParameters = parameters {
                append("year", year)
                append("month", month)
            }
        )
    }

    suspend fun getAttendanceFilter(): HttpResponse {
        return client.submitForm(ApiEndpoints.API_ATTENDANCE_FILTER)
    }

    suspend fun getTimeRequestVerifiers(): HttpResponse {
        return client.get(ApiEndpoints.API_TIME_REQUEST_VERIFIERS)
    }

    suspend fun getApplyTimeRequest(
        nep_date: String,
        actual_in_time: String,
        actual_in_time_remarks: String,
        actual_out_time: String,
        actual_out_time_remarks: String,
        verifier_id: String,
        documents: List<PlatformFile>
    ): HttpResponse {
        return client.submitFormWithBinaryData(
            url = ApiEndpoints.API_APPLY_TIME_REQUEST,
            formData = formData {
                append("nep_date", nep_date)
                append("actual_in_time", actual_in_time)
                append("actual_in_time_remarks", actual_in_time_remarks)
                append("actual_out_time", actual_out_time)
                append("actual_out_time_remarks", actual_out_time_remarks)
                append("verifier_id", verifier_id)
                documents.forEachIndexed { index, document ->
                    val byteArray = runBlocking { document.readBytes() }
                    append("documents[$index]", byteArray, Headers.build {
                        append(
                            HttpHeaders.ContentType,
                            getFileExtensionType<FileExtensionType>(document.extension).contentType
                        )
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=${document.name}"
                        )
                    })
                }
            }
        )
    }

    suspend fun getTimeRequestEdit(ticket_id: String): HttpResponse {
        return client.get(ApiEndpoints.API_TIME_REQUEST_EDIT(ticket_id))
    }

    suspend fun getTimeRequestUpdate(
        ticket_id: String,
        verifier_id: String,
        nep_date: String,
        actual_in_time: String,
        actual_in_time_remarks: String,
        actual_out_time: String,
        actual_out_time_remarks: String,
        documents: List<PlatformFile>
    ): HttpResponse {
        return client.submitFormWithBinaryData(
            url = ApiEndpoints.API_TIME_REQUEST_UPDATE(ticket_id),
            formData = formData {
                append("ticket_id", ticket_id)
                append("verifier_id", verifier_id)
                append("nep_date", nep_date)
                append("actual_in_time", actual_in_time)
                append("actual_in_time_remarks", actual_in_time_remarks)
                append("actual_out_time", actual_out_time)
                append("actual_out_time_remarks", actual_out_time_remarks)
                documents.forEachIndexed { index, document ->
                    val byteArray = runBlocking { document.readBytes() }
                    append("documents[$index]", byteArray, Headers.build {
                        append(
                            HttpHeaders.ContentType,
                            getFileExtensionType<FileExtensionType>(document.extension).contentType
                        )
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=${document.name}"
                        )
                    })
                }
            }
        )
    }

    suspend fun getLeaveRequestVerifiers(): HttpResponse {
        return client.get(ApiEndpoints.API_LEAVE_REQUEST_VERIFIERS)
    }

    @OptIn(InternalAPI::class)
    suspend fun getApplyLeaveRequest(
        leave_type_id: String,
        leave_option_id: String,
        nep_start_date: String,
        nep_end_date: String,
        verifier_id: String,
        documents: List<PlatformFile>,
        remarks: String
    ): HttpResponse {
        return client.submitFormWithBinaryData(
            url = ApiEndpoints.API_APPLY_LEAVE_REQUEST,
            formData = formData {
                append("leave_type_id", leave_type_id)
                append("leave_option_id", leave_option_id)
                append("nep_start_date", nep_start_date)
                append("nep_end_date", nep_end_date)
                append("verifier_id", verifier_id)
                documents.forEachIndexed { index, document ->
                    val byteArray = runBlocking { document.readBytes() }
                    append("documents[$index]", byteArray, Headers.build {
                        append(
                            HttpHeaders.ContentType,
                            getFileExtensionType<FileExtensionType>(document.extension).contentType
                        )
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=${document.name}"
                        )
                    })
                }
                append("remarks", remarks)
            }
        )
    }

    suspend fun getLeaveDetails(): HttpResponse {
        return client.get(ApiEndpoints.API_LEAVE_DETAILS) {
        }
    }

    suspend fun getLeaveRequestEdit(ticket_id: String): HttpResponse {
        return client.get(ApiEndpoints.API_LEAVE_DETAILS_EDIT(ticket_id))
    }

    suspend fun getLeaveRequestUpdate(
        ticket_id: String,
        verifier_id: String,
        leave_type_id: String,
        leave_option_id: String,
        nep_start_date: String,
        nep_end_date: String,
        documents: List<PlatformFile>,
        remarks: String
    ): HttpResponse {
        return client.submitFormWithBinaryData(
            url = ApiEndpoints.API_LEAVE_DETAILS_UPDATE(ticket_id),
            formData = formData {
                append("ticket_id", ticket_id)
                append("leave_type_id", leave_type_id)
                append("leave_option_id", leave_option_id)
                append("nep_start_date", nep_start_date)
                append("nep_end_date", nep_end_date)
                append("verifier_id", verifier_id)
                documents.forEachIndexed { index, document ->
                    val byteArray = runBlocking { document.readBytes() }
                    append("documents[$index]", byteArray, Headers.build {
                        append(
                            HttpHeaders.ContentType,
                            getFileExtensionType<FileExtensionType>(document.extension).contentType
                        )
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=${document.name}"
                        )
                    })
                }
                append("remarks", remarks)
            }
        )
    }

    suspend fun getDocumentRemove(ticket_id: String, document_id: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_REMOVE_DOCUMENT,
            formParameters = parameters {
                append("ticket_id", ticket_id)
                append("document_id", document_id)
            })
    }

    suspend fun getRecordedTime(date: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_RECORDED_TIME,
            formParameters = parameters {
                append("date", date)
            })
    }

    suspend fun getTicket(ticketType: String, workflow: String, page: Int): HttpResponse {
        return client.get(ApiEndpoints.API_TICKET) {
            url {
                parameters.append("ticketType", ticketType)
                parameters.append("workflow", workflow)
                parameters.append("page", page.toString())
            }
        }
    }

    suspend fun getTicketFilter(ticketType: String): HttpResponse {
        return client.get(ApiEndpoints.API_TICKET_FILTER) {
            url {
                parameters.append("ticketType", ticketType)
            }
        }
    }

    suspend fun getTicketDetails(ticket_id: String): HttpResponse {
        return client.get(ApiEndpoints.API_TICKET_DETAILS(ticket_id))
    }

    suspend fun getTicketChangeState(
        ticket_id: String,
        state: String,
        comment: String,
        next_owner_id: String
    ): HttpResponse {

        return client.submitForm(
            url = ApiEndpoints.API_TICKET_CHANGE_STATE,
            formParameters = parameters {
                append("ticket_id", ticket_id)
                append("state", state)
                append("comment", comment)
                append("next_owner_id", next_owner_id)
            })
    }

    suspend fun getTicketChangeOwner(ticket_id: String, next_owner_id: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_TICKET_CHANGE_OWNER,
            formParameters = parameters {
                append("ticket_id", ticket_id)
                append("next_owner_id", next_owner_id)
            })
    }

    suspend fun getNotification(): HttpResponse {
        return client.get(ApiEndpoints.API_NOTIFICATION)
    }

    suspend fun getNotificationMarkAsAllRead(): HttpResponse {
        return client.submitForm(url = ApiEndpoints.API_NOTIFICATION_MARK_AS_ALL_READ)
    }

    suspend fun getNotificationMarkAsRead(id: String): HttpResponse {
        return client.submitForm(
            url = ApiEndpoints.API_NOTIFICATION_MARK_AS_READ,
            formParameters = parameters {
                append("id", id)
            })
    }

    suspend fun getLogout(): HttpResponse {
        return client.get(ApiEndpoints.API_LOGOUT)
    }

}