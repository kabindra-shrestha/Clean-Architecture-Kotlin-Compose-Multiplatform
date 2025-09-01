package com.kabindra.clean.architecture.domain.entity

import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class TicketFilter(
    val response: TicketFilterInfo?
) : BaseResponse()

@Serializable
data class TicketFilterInfo(
    val workflows: List<TicketFilterWorkFlows>? = listOf(),
    val employees: List<TicketFilterEmployees>? = listOf()
)

@Serializable
data class TicketFilterWorkFlows(
    val title: String? = "",
    val value: String? = ""
)

@Serializable
data class TicketFilterEmployees(
    val name: String? = "",
    val employee_code: String? = ""
)