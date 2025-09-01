package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.data.request.ApplyTimeRequestDataRequest
import com.kabindra.clean.architecture.data.request.TimeRequestUpdateDataRequest
import com.kabindra.clean.architecture.domain.entity.ApplyTime
import com.kabindra.clean.architecture.domain.entity.EditTimeRequest
import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifier
import com.kabindra.clean.architecture.domain.entity.UpdateTimeRequest
import com.kabindra.clean.architecture.domain.repository.remote.TimeRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class TimeUseCase(private val repository: TimeRepository) {

    suspend fun executeGetTimeRequestVerifiers(): Flow<Result<TimeRequestVerifier>> {
        return repository.getTimeRequestVerifiers()
    }

    suspend fun executeGetApplyTimeRequest(applyTimeRequestDataRequest: ApplyTimeRequestDataRequest): Flow<Result<ApplyTime>> {
        return repository.getApplyTimeRequest(applyTimeRequestDataRequest)
    }

    suspend fun executeGetTimeRequestEdit(ticket_id: String): Flow<Result<EditTimeRequest>> {
        return repository.getTimeRequestEdit(ticket_id)
    }

    suspend fun executeGetTimeRequestUpdate(timeRequestUpdateDataRequest: TimeRequestUpdateDataRequest): Flow<Result<UpdateTimeRequest>> {
        return repository.getTimeRequestUpdate(timeRequestUpdateDataRequest)
    }

}

