package com.kabindra.clean.architecture.domain.usecase.remote

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
import com.kabindra.clean.architecture.domain.repository.remote.TicketRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class TicketUseCase(private val repository: TicketRepository) {
    suspend fun executeGetTicket(ticketDataRequest: TicketDataRequest): Flow<Result<Ticket>> {
        return repository.getTicket(ticketDataRequest)
    }

    suspend fun executeGetTicketFilter(ticketFilterDataRequest: TicketFilterDataRequest): Flow<Result<TicketFilter>> {
        return repository.getTicketFilter(ticketFilterDataRequest)
    }

    suspend fun executeGetTicketDetails(ticketDetailsRequest: TicketDetailsRequest): Flow<Result<TicketDetails>> {
        return repository.getTicketDetails(ticketDetailsRequest)
    }

    suspend fun executeGetTicketChangeState(ticketChangeStateRequest: TicketChangeStateRequest): Flow<Result<TicketChangeState>> {
        return repository.getTicketChangeState(ticketChangeStateRequest)
    }

    suspend fun executeGetTicketChangeOwner(ticketChangeOwnerRequest: TicketChangeOwnerRequest): Flow<Result<TicketChangeOwner>> {
        return repository.getTicketChangeOwner(ticketChangeOwnerRequest)
    }
}
