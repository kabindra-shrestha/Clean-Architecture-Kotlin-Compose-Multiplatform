package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.Access
import com.kabindra.clean.architecture.domain.entity.Action
import com.kabindra.clean.architecture.domain.entity.Actions
import com.kabindra.clean.architecture.domain.entity.Actor
import com.kabindra.clean.architecture.domain.entity.CurrentOwner
import com.kabindra.clean.architecture.domain.entity.Detail
import com.kabindra.clean.architecture.domain.entity.Employee
import com.kabindra.clean.architecture.domain.entity.LeaveBalance
import com.kabindra.clean.architecture.domain.entity.Owner
import com.kabindra.clean.architecture.domain.entity.StateHistory
import com.kabindra.clean.architecture.domain.entity.TicketDetails
import com.kabindra.clean.architecture.domain.entity.TicketDetailsInfo
import com.kabindra.clean.architecture.domain.entity.TicketDocument
import com.kabindra.clean.architecture.domain.entity.TicketHistoryDocument
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TicketDetailsDTO(
    val response: TicketDetailsInfoDTO?
) : BaseResponse()

@Serializable
data class TicketDetailsInfoDTO(
    val workflow: String? = "",
    val detail: DetailDTO?,
    val employee: EmployeeDTO?,
    val current_owner: CurrentOwnerDTO?,
    val access: AccessDTO?,
    val actions: ActionsDTO?,
    val documents: List<TicketDocumentDTO>? = listOf(),
    val state_history: List<StateHistoryDTO>? = listOf()
)

@Serializable
data class DetailDTO(
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
    val leave_balance: List<LeaveBalanceDTO>? = listOf(),
    val date: String? = "",
    val device_in: String? = "",
    val requested_in: String? = "",
    val in_note: String? = "",
    val device_out: String? = "",
    val requested_out: String? = "",
    val out_note: String? = ""
)

@Serializable
data class LeaveBalanceDTO(
    val name: String? = "",
    val assigned_leave: Double? = 0.0,
    val leave_taken: Double? = 0.0,
    val pending_leave: Double? = 0.0
)

@Serializable
data class EmployeeDTO(
    val name: String? = "",
    val employee_code: String? = "",
    val department: String? = "",
    val branch: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class CurrentOwnerDTO(
    val id: Int? = 0,
    val name: String? = "",
    val employee_code: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class AccessDTO(
    val approve: Boolean? = false,
    val cancel: Boolean? = false,
    val edit: Boolean? = false,
    val change_owner: Boolean? = false,
    val reject: Boolean? = false,
    val revert: Boolean? = false,
    val verify: Boolean? = false
)

@Serializable
data class ActionsDTO(
    val approve: ActionDTO?,
    val cancel: ActionDTO?,
    val change_owner: ActionDTO?,
    val reject: ActionDTO?,
    val revert: ActionDTO?,
    val verify: ActionDTO?
)

@Serializable
data class ActionDTO(
    val owners: List<OwnerDTO>? = listOf(),
    val remarks: String? = ""
)

@Serializable
data class OwnerDTO(
    val id: Int? = 0,
    val name: String? = "",
    val employee_code: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class StateHistoryDTO(
    val title: String? = "",
    val state: String? = "",
    val remarks: String? = "",
    val time: String? = "",
    val actor: ActorDTO?,
    val documents: List<TicketHistoryDocumentDTO>? = listOf()
)

@Serializable
data class ActorDTO(
    val id: Int? = 0,
    val name: String? = "",
    val employee_code: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class TicketDocumentDTO(
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
data class TicketHistoryDocumentDTO(
    val document: String? = "",
    val extension: String? = ""
)

// Mapper function
fun TicketDetailsDTO.toDomain(): TicketDetails {
    return TicketDetails(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun TicketDetailsInfoDTO.toDomain(): TicketDetailsInfo {
    return TicketDetailsInfo(
        workflow = workflow,
        detail = detail?.toDomain(),
        employee = employee?.toDomain(),
        current_owner = current_owner?.toDomain(),
        access = access?.toDomain(),
        actions = actions?.toDomain(),
        documents = documents?.map { it.toDomain() },
        state_history = state_history?.map { it.toDomain() },
    )
}

fun DetailDTO.toDomain(): Detail {
    return Detail(
        ticket_id = ticket_id,
        state = state,
        applied_on = applied_on,
        start_date = start_date,
        end_date = end_date,
        title = title,
        subtitle = subtitle,
        leave_type = leave_type,
        time_request_count = time_request_count,
        leave_option = leave_option,
        num_days = num_days,
        applied_status = applied_status,
        remarks = remarks,
        leave_count = leave_count,
        leave_balance = leave_balance?.map { it.toDomain() },
        date = date,
        device_in = device_in,
        requested_in = requested_in,
        in_note = in_note,
        device_out = device_out,
        requested_out = requested_out,
        out_note = out_note
    )
}

fun LeaveBalanceDTO.toDomain(): LeaveBalance {
    return LeaveBalance(
        name = name,
        assigned_leave = assigned_leave,
        leave_taken = leave_taken,
        pending_leave = pending_leave
    )
}

fun EmployeeDTO.toDomain(): Employee {
    return Employee(
        name = name,
        employee_code = employee_code,
        department = department,
        branch = branch,
        profile_picture = profile_picture
    )
}

fun CurrentOwnerDTO.toDomain(): CurrentOwner {
    return CurrentOwner(
        id = id,
        name = name,
        employee_code = employee_code,
        profile_picture = profile_picture
    )
}

fun AccessDTO.toDomain(): Access {
    return Access(
        approve = approve,
        cancel = cancel,
        edit = edit,
        change_owner = change_owner,
        reject = reject,
        revert = revert,
        verify = verify
    )
}

fun ActionsDTO.toDomain(): Actions {
    return Actions(
        approve = approve?.toDomain(),
        cancel = cancel?.toDomain(),
        change_owner = change_owner?.toDomain(),
        reject = reject?.toDomain(),
        revert = revert?.toDomain(),
        verify = verify?.toDomain()
    )
}

fun ActionDTO.toDomain(): Action {
    return Action(
        owners = owners?.map { it.toDomain() },
        remarks = remarks
    )
}

fun OwnerDTO.toDomain(): Owner {
    return Owner(
        id = id,
        name = name,
        employee_code = employee_code,
        profile_picture = profile_picture
    )
}

fun StateHistoryDTO.toDomain(): StateHistory {
    return StateHistory(
        title = title,
        state = state,
        remarks = remarks,
        time = time,
        actor = actor?.toDomain(),
        documents = documents?.map { it.toDomain() },
    )
}

fun ActorDTO.toDomain(): Actor {
    return Actor(
        id = id,
        name = name,
        employee_code = employee_code,
        profile_picture = profile_picture
    )
}

fun TicketDocumentDTO.toDomain(): TicketDocument {
    return TicketDocument(
        id = id,
        name = name,
        path = path,
        owner_name = owner_name,
        icon = icon,
        state = state,
        date_added = date_added,
        canRemove = canRemove,
        extension = extension
    )
}

fun TicketHistoryDocumentDTO.toDomain(): TicketHistoryDocument {
    return TicketHistoryDocument(
        document = document,
        extension = extension
    )
}