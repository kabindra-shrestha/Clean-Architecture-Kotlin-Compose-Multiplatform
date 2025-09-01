package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.MPINSetDataRequest
import com.kabindra.clean.architecture.data.request.MPINVerifyDataRequest
import com.kabindra.clean.architecture.domain.entity.MPINSet
import com.kabindra.clean.architecture.domain.entity.MPINVerify
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface MPINRepository {
    suspend fun getMPINSet(mPINSetDataRequest: MPINSetDataRequest): Flow<Result<MPINSet>>
    suspend fun getMPINVerify(mPINVerifyDataRequest: MPINVerifyDataRequest): Flow<Result<MPINVerify>>
}