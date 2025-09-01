package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.ApplyLeaveRequestDataRequest
import com.kabindra.clean.architecture.data.request.LeaveRequestUpdateDataRequest
import com.kabindra.clean.architecture.domain.entity.ApplyLeave
import com.kabindra.clean.architecture.domain.entity.EditLeaveRequest
import com.kabindra.clean.architecture.domain.entity.LeaveDetails
import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifier
import com.kabindra.clean.architecture.domain.entity.UpdateLeaveRequest
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface LeaveRepository {
    suspend fun getApplyLeaveRequest(applyTimeRequestDataRequest: ApplyLeaveRequestDataRequest): Flow<Result<ApplyLeave>>
    suspend fun getLeaveRequestVerifiers(): Flow<Result<LeaveRequestVerifier>>
    suspend fun getLeaveDetails(): Flow<Result<LeaveDetails>>
    suspend fun getLeaveRequestEdit(ticket_id: String): Flow<Result<EditLeaveRequest>>
    suspend fun getLeaveRequestUpdate(applyTimeRequestUpdateDataRequest: LeaveRequestUpdateDataRequest): Flow<Result<UpdateLeaveRequest>>

}
