package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BasePaginationResponse
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val response: TicketInfo? = null
) : BaseResponse()

@Serializable
data class TicketInfo(
    val data: List<TicketData>? = listOf()
) : BasePaginationResponse()

@Serializable
data class TicketData(
    val workflow: String? = "",
    val detail: TicketDetail? = null,
    val employee: TicketEmployee? = null,
    val current_owner: TicketCurrentOwner? = null,
    val access: TicketAccess? = null,
    val actions: TicketActions? = null
)

@Serializable
data class TicketDetail(
    val ticket_id: Int? = 0,
    val state: String? = "",
    val applied_on: String? = "",
    val start_date: String? = "",
    val end_date: String? = "",
    val title: String? = "",
    val subtitle: String? = "",
    val num_days: Double? = 0.0,
    val applied_status: String? = "",
    val remarks: String? = "",
    val date: String? = "",
    val device_in: String? = "",
    val requested_in: String? = "",
    val in_note: String? = "",
    val device_out: String? = "",
    val requested_out: String? = "",
    val out_note: String? = ""
)

@Serializable
data class TicketEmployee(
    val name: String? = "",
    val employee_code: String? = "",
    val department: String? = "",
    val branch: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class TicketCurrentOwner(
    val name: String? = "",
    val employee_code: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class TicketAccess(
    val approve: Boolean? = false,
    val cancel: Boolean? = false,
    val edit: Boolean? = false
)

@Serializable
data class TicketActions(
    val approve: TicketAction? = null,
    val cancel: TicketAction? = null
)

@Serializable
data class TicketAction(
    val owners: List<Owner>? = listOf(),
    val remarks: String? = ""
)