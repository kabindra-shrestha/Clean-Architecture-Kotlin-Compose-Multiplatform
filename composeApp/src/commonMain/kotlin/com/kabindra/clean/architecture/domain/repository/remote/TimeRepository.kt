package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.ApplyTimeRequestDataRequest
import com.kabindra.clean.architecture.data.request.TimeRequestUpdateDataRequest
import com.kabindra.clean.architecture.domain.entity.ApplyTime
import com.kabindra.clean.architecture.domain.entity.EditTimeRequest
import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifier
import com.kabindra.clean.architecture.domain.entity.UpdateTimeRequest
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface TimeRepository {
    suspend fun getTimeRequestVerifiers(): Flow<Result<TimeRequestVerifier>>
    suspend fun getApplyTimeRequest(applyTimeRequestDataRequest: ApplyTimeRequestDataRequest): Flow<Result<ApplyTime>>
    suspend fun getTimeRequestEdit(ticket_id: String): Flow<Result<EditTimeRequest>>
    suspend fun getTimeRequestUpdate(timeRequestUpdateDataRequest: TimeRequestUpdateDataRequest): Flow<Result<UpdateTimeRequest>>

}
