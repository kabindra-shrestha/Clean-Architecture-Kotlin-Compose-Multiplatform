package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.TicketChangeOwnerRequest
import com.kabindra.clean.architecture.data.request.TicketChangeStateRequest
import com.kabindra.clean.architecture.data.request.TicketDataRequest
import com.kabindra.clean.architecture.data.request.TicketDetailsRequest
import com.kabindra.clean.architecture.data.request.TicketFilterDataRequest
import com.kabindra.clean.architecture.domain.entity.Ticket
import com.kabindra.clean.architecture.domain.entity.TicketChangeOwner
import com.kabindra.clean.architecture.domain.entity.TicketChangeState
import com.kabindra.clean.architecture.domain.entity.TicketDetails
import com.kabindra.clean.architecture.domain.entity.TicketFilter
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    suspend fun getTicket(ticketDataRequest: TicketDataRequest): Flow<Result<Ticket>>
    suspend fun getTicketFilter(ticketFilterDataRequest: TicketFilterDataRequest): Flow<Result<TicketFilter>>
    suspend fun getTicketDetails(ticketDetailsRequest: TicketDetailsRequest): Flow<Result<TicketDetails>>
    suspend fun getTicketChangeState(ticketChangeStateRequest: TicketChangeStateRequest): Flow<Result<TicketChangeState>>
    suspend fun getTicketChangeOwner(ticketChangeOwnerRequest: TicketChangeOwnerRequest): Flow<Result<TicketChangeOwner>>
}
