package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.TicketChangeOwnerDTO
import com.kabindra.clean.architecture.data.model.TicketChangeStateDTO
import com.kabindra.clean.architecture.data.model.TicketDTO
import com.kabindra.clean.architecture.data.model.TicketDetailsDTO
import com.kabindra.clean.architecture.data.model.TicketFilterDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.request.TicketChangeOwnerRequest
import com.kabindra.clean.architecture.data.request.TicketChangeStateRequest
import com.kabindra.clean.architecture.data.request.TicketDataRequest
import com.kabindra.clean.architecture.data.request.TicketDetailsRequest
import com.kabindra.clean.architecture.data.request.TicketFilterDataRequest
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.domain.entity.Ticket
import com.kabindra.clean.architecture.domain.entity.TicketChangeOwner
import com.kabindra.clean.architecture.domain.entity.TicketChangeState
import com.kabindra.clean.architecture.domain.entity.TicketDetails
import com.kabindra.clean.architecture.domain.entity.TicketFilter
import com.kabindra.clean.architecture.domain.repository.remote.TicketRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TicketRepositoryImpl(
    private val apiDataSource: ApiDataSource
) : TicketRepository {

    override suspend fun getTicket(ticketDataRequest: TicketDataRequest): Flow<Result<Ticket>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse = apiDataSource.getTicket(ticketDataRequest)
                if (response.status.isSuccess()) {
                    val responses: TicketDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        emit(Result.Success(responses.toDomain()))
                    } else {
                        emit(Result.Error(ResultError.parseError(response)))
                    }
                } else {
                    emit(Result.Error(ResultError.parseError(response)))
                }
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

    override suspend fun getTicketFilter(ticketFilterDataRequest: TicketFilterDataRequest): Flow<Result<TicketFilter>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse = apiDataSource.getTicketFilter(ticketFilterDataRequest)
                if (response.status.isSuccess()) {
                    val responses: TicketFilterDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        emit(Result.Success(responses.toDomain()))
                    } else {
                        emit(Result.Error(ResultError.parseError(response)))
                    }
                } else {
                    emit(Result.Error(ResultError.parseError(response)))
                }
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

    override suspend fun getTicketDetails(ticketDetailsRequest: TicketDetailsRequest): Flow<Result<TicketDetails>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse = apiDataSource.getTicketDetails(ticketDetailsRequest)
                if (response.status.isSuccess()) {
                    val responses: TicketDetailsDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        emit(Result.Success(responses.toDomain()))
                    } else {
                        emit(Result.Error(ResultError.parseError(response)))
                    }
                } else {
                    emit(Result.Error(ResultError.parseError(response)))
                }
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

    override suspend fun getTicketChangeState(ticketChangeStateRequest: TicketChangeStateRequest): Flow<Result<TicketChangeState>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getTicketChangeState(ticketChangeStateRequest)
                if (response.status.isSuccess()) {
                    val responses: TicketChangeStateDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        emit(Result.Success(responses.toDomain()))
                    } else {
                        emit(Result.Error(ResultError.parseError(response)))
                    }
                } else {
                    emit(Result.Error(ResultError.parseError(response)))
                }
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }

    override suspend fun getTicketChangeOwner(ticketChangeOwnerRequest: TicketChangeOwnerRequest): Flow<Result<TicketChangeOwner>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getTicketChangeOwner(ticketChangeOwnerRequest)
                if (response.status.isSuccess()) {
                    val responses: TicketChangeOwnerDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        emit(Result.Success(responses.toDomain()))
                    } else {
                        emit(Result.Error(ResultError.parseError(response)))
                    }
                } else {
                    emit(Result.Error(ResultError.parseError(response)))
                }
            } catch (e: Exception) {
                emit(Result.Error(ResultError.parseException(e)))
            }
        }
}


