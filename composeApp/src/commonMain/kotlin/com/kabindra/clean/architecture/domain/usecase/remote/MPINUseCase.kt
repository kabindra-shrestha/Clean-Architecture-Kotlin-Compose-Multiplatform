package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.data.request.MPINSetDataRequest
import com.kabindra.clean.architecture.data.request.MPINVerifyDataRequest
import com.kabindra.clean.architecture.domain.entity.MPINSet
import com.kabindra.clean.architecture.domain.entity.MPINVerify
import com.kabindra.clean.architecture.domain.repository.remote.MPINRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class MPINUseCase(private val repository: MPINRepository) {
    suspend fun executeGetMPINSet(mPINSetDataRequest: MPINSetDataRequest): Flow<Result<MPINSet>> {
        return repository.getMPINSet(mPINSetDataRequest)
    }

    suspend fun executeGetMPINVerify(mPINVerifyDataRequest: MPINVerifyDataRequest): Flow<Result<MPINVerify>> {
        return repository.getMPINVerify(mPINVerifyDataRequest)
    }
}
