package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.Ticket
import com.kabindra.clean.architecture.domain.entity.TicketAccess
import com.kabindra.clean.architecture.domain.entity.TicketAction
import com.kabindra.clean.architecture.domain.entity.TicketActions
import com.kabindra.clean.architecture.domain.entity.TicketCurrentOwner
import com.kabindra.clean.architecture.domain.entity.TicketData
import com.kabindra.clean.architecture.domain.entity.TicketDetail
import com.kabindra.clean.architecture.domain.entity.TicketEmployee
import com.kabindra.clean.architecture.domain.entity.TicketInfo
import com.kabindra.clean.architecture.utils.base.BasePaginationResponse
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TicketDTO(
    val response: TicketInfoDTO? = null
) : BaseResponse()

@Serializable
data class TicketInfoDTO(
    val data: List<TicketDataDTO>? = listOf()
) : BasePaginationResponse()

@Serializable
data class TicketDataDTO(
    val workflow: String? = "",
    val detail: TicketDetailDTO? = null,
    val employee: TicketEmployeeDTO? = null,
    val current_owner: TicketCurrentOwnerDTO? = null,
    val access: TicketAccessDTO? = null,
    val actions: TicketActionsDTO? = null
)

@Serializable
data class TicketDetailDTO(
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
data class TicketEmployeeDTO(
    val name: String? = "",
    val employee_code: String? = "",
    val department: String? = "",
    val branch: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class TicketCurrentOwnerDTO(
    val name: String? = "",
    val employee_code: String? = "",
    val profile_picture: String? = ""
)

@Serializable
data class TicketAccessDTO(
    val approve: Boolean? = false,
    val cancel: Boolean? = false,
    val edit: Boolean? = false
)

@Serializable
data class TicketActionsDTO(
    val approve: TicketActionDTO? = null,
    val cancel: TicketActionDTO? = null
)

@Serializable
data class TicketActionDTO(
    val owners: List<OwnerDTO>? = listOf(),
    val remarks: String? = ""
)

// Mapper function
fun TicketDTO.toDomain(): Ticket {
    return Ticket(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun TicketInfoDTO.toDomain(): TicketInfo {
    return TicketInfo(
        data = data!!.map { it.toDomain() }
    ).apply {
        current_page = this@toDomain.current_page
        per_page = this@toDomain.per_page
        total = this@toDomain.total
        last_page = this@toDomain.last_page
    }
}

fun TicketDataDTO.toDomain(): TicketData {
    return TicketData(
        workflow = workflow,
        detail = detail?.toDomain(),
        employee = employee?.toDomain(),
        current_owner = current_owner?.toDomain(),
        access = access?.toDomain(),
        actions = actions?.toDomain()
    )
}

fun TicketDetailDTO.toDomain(): TicketDetail {
    return TicketDetail(
        ticket_id = ticket_id,
        state = state,
        applied_on = applied_on,
        start_date = start_date,
        end_date = end_date,
        title = title,
        subtitle = subtitle,
        num_days = num_days,
        applied_status = applied_status,
        remarks = remarks,
        date = date,
        device_in = device_in,
        requested_in = requested_in,
        in_note = in_note,
        device_out = device_out,
        requested_out = requested_out,
        out_note = out_note
    )
}

fun TicketEmployeeDTO.toDomain(): TicketEmployee {
    return TicketEmployee(
        name = name,
        employee_code = employee_code,
        department = department,
        branch = branch,
        profile_picture = profile_picture
    )
}

fun TicketCurrentOwnerDTO.toDomain(): TicketCurrentOwner {
    return TicketCurrentOwner(
        name = name,
        employee_code = employee_code,
        profile_picture = profile_picture
    )
}

fun TicketAccessDTO.toDomain(): TicketAccess {
    return TicketAccess(
        approve = approve,
        cancel = cancel,
        edit = edit
    )
}

fun TicketActionsDTO.toDomain(): TicketActions {
    return TicketActions(
        approve = approve?.toDomain(),
        cancel = cancel?.toDomain()
    )
}

fun TicketActionDTO.toDomain(): TicketAction {
    return TicketAction(
        owners = owners?.map { it.toDomain() },
        remarks = remarks
    )
}