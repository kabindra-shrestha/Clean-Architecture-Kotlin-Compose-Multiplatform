package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.data.request.ApplyLeaveRequestDataRequest
import com.kabindra.clean.architecture.data.request.LeaveRequestUpdateDataRequest
import com.kabindra.clean.architecture.domain.entity.ApplyLeave
import com.kabindra.clean.architecture.domain.entity.EditLeaveRequest
import com.kabindra.clean.architecture.domain.entity.LeaveDetails
import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifier
import com.kabindra.clean.architecture.domain.entity.UpdateLeaveRequest
import com.kabindra.clean.architecture.domain.repository.remote.LeaveRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class LeaveUseCase(private val repository: LeaveRepository) {
    suspend fun executeGetApplyLeaveRequest(applyLeaveRequestDataRequest: ApplyLeaveRequestDataRequest): Flow<Result<ApplyLeave>> {
        return repository.getApplyLeaveRequest(applyLeaveRequestDataRequest)
    }

    suspend fun executeGetLeaveRequestVerifiers(): Flow<Result<LeaveRequestVerifier>> {
        return repository.getLeaveRequestVerifiers()
    }

    suspend fun executeGetLeaveDetails(): Flow<Result<LeaveDetails>> {
        return repository.getLeaveDetails()
    }

    suspend fun executeGetLeaveRequestEdit(ticket_id: String): Flow<Result<EditLeaveRequest>> {
        return repository.getLeaveRequestEdit(ticket_id)
    }

    suspend fun executeGetLeaveUpdate(applyTimeRequestUpdateDataRequest: LeaveRequestUpdateDataRequest): Flow<Result<UpdateLeaveRequest>> {
        return repository.getLeaveRequestUpdate(applyTimeRequestUpdateDataRequest)
    }

}

