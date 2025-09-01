package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.data.request.TicketDocumentRemoveRequest
import com.kabindra.clean.architecture.domain.entity.TicketDocumentRemove
import com.kabindra.clean.architecture.domain.repository.remote.DocumentRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class DocumentUseCase(private val documentRepository: DocumentRepository) {
    suspend fun executeGetDocumentRemove(ticketDocumentRemoveRequest: TicketDocumentRemoveRequest): Flow<Result<TicketDocumentRemove>> {
        return documentRepository.getDocumentRemove(ticketDocumentRemoveRequest)
    }
}

