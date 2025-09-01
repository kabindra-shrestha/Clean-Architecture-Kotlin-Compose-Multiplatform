package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.TicketDocumentRemoveRequest
import com.kabindra.clean.architecture.domain.entity.TicketDocumentRemove
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {
    suspend fun getDocumentRemove(ticketDocumentRemoveRequest: TicketDocumentRemoveRequest): Flow<Result<TicketDocumentRemove>>
}
