package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TicketDetails(
    val response: TicketDetailsInfo?
) : BaseResponse()

@Serializable
data class TicketDetailsInfo(
    val workflow: String? = "",
    val detail: Detail?,
    val employee: Employee?,
    val current_owner: CurrentOwner?,
    val access: Access?,
    val actions: Actions?,
    val documents: List<TicketDocument>? = listOf(),
    val state_history: List<StateHistory>? = listOf()
)

@Serializable
data class Detail(
    val ticket_id: Int? = 0,
    val state: String? = "",
    val applied_on: String? = "",
    val start_date: String? = "",
    val end_date: String? = "",
    val title: String? = "",
    val subtitle: String? = "",
    val leave_type: String? = "",
    val leave_option: String? = "",
    val num_days: Double? = 0.0,
    val applied_status: String? = "",
    val remarks: String? = "",
    val time_request_count: Int? = 0,
    val leave_count: Double? = 0.0,
    val leave_balance: List<LeaveBalance>? = listOf(),
    val date: String? = "",
    val device_in: String? = "",
    val requested_in: String? = "",
    val in_note: String? = "",
    val device_out: String? = "",
    val requested_out: String? = "",
    val out_note: String? = ""
)

@Serializable
data class LeaveBalance(
    val name: String? = "",
    val assigned_leave: Double? = 0.0,
    val leave_taken: Double? = 0.0,
    val pending_leave: Double? = 0.0
)

@Serializable
data class Employee(
    val name: String? = "",
    val employee_code: String? = "",
    val department: String? = "",
    val branch: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class CurrentOwner(
    val id: Int? = 0,
    val name: String? = "",
    val employee_code: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class Access(
    val approve: Boolean? = false,
    val cancel: Boolean? = false,
    val edit: Boolean? = false,
    val change_owner: Boolean? = false,
    val reject: Boolean? = false,
    val revert: Boolean? = false,
    val verify: Boolean? = false
)

@Serializable
data class Actions(
    val approve: Action?,
    val cancel: Action?,
    val change_owner: Action?,
    val reject: Action?,
    val revert: Action?,
    val verify: Action?
)

@Serializable
data class Action(
    val owners: List<Owner>? = listOf(),
    val remarks: String? = ""
)

@Serializable
data class Owner(
    val id: Int? = 0,
    val name: String? = "",
    val employee_code: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class StateHistory(
    val title: String? = "",
    val state: String? = "",
    val remarks: String? = "",
    val time: String? = "",
    val actor: Actor?,
    val documents: List<TicketHistoryDocument>? = listOf()
)

@Serializable
data class Actor(
    val id: Int? = 0,
    val name: String? = "",
    val employee_code: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class TicketDocument(
    val id: Int? = 0,
    val name: String? = "",
    val path: String? = "",
    val owner_name: String? = "",
    val icon: String? = "",
    val state: String? = "",
    val date_added: String? = "",
    val canRemove: Boolean? = false,
    val extension: String? = ""
)

@Serializable
data class TicketHistoryDocument(
    val document: String? = "",
    val extension: String? = ""
)