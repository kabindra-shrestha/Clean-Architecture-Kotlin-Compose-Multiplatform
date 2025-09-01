package com.kabindra.clean.architecture.data.model

import com.kabindra.clean.architecture.domain.entity.TicketFilter
import com.kabindra.clean.architecture.domain.entity.TicketFilterEmployees
import com.kabindra.clean.architecture.domain.entity.TicketFilterInfo
import com.kabindra.clean.architecture.domain.entity.TicketFilterWorkFlows
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TicketFilterDTO(
    val response: TicketFilterInfoDTO?
) : BaseResponse()

@Serializable
data class TicketFilterInfoDTO(
    val workflows: List<TicketFilterWorkFlowsDTO>? = listOf(),
    val employees: List<TicketFilterEmployeesDTO>? = listOf()
)

@Serializable
data class TicketFilterWorkFlowsDTO(
    val title: String? = "",
    val value: String? = ""
)

@Serializable
data class TicketFilterEmployeesDTO(
    val name: String? = "",
    val employee_code: String? = ""
)

// Mapper function
fun TicketFilterDTO.toDomain(): TicketFilter {
    return TicketFilter(
        response = response?.toDomain()
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun TicketFilterInfoDTO.toDomain(): TicketFilterInfo {
    return TicketFilterInfo(
        workflows = workflows!!.map { it.toDomain() },
        employees = employees!!.map { it.toDomain() }
    )
}

fun TicketFilterWorkFlowsDTO.toDomain(): TicketFilterWorkFlows {
    return TicketFilterWorkFlows(
        title = title,
        value = value
    )
}

fun TicketFilterEmployeesDTO.toDomain(): TicketFilterEmployees {
    return TicketFilterEmployees(
        name = name,
        employee_code = employee_code
    )
}