package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.data.request.LoginSendOTPDataRequest
import com.kabindra.clean.architecture.domain.entity.LoginCheckUser
import com.kabindra.clean.architecture.domain.entity.LoginRefreshUserDetails
import com.kabindra.clean.architecture.domain.entity.LoginVerify
import com.kabindra.clean.architecture.domain.repository.remote.LoginRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repository: LoginRepository) {
    suspend fun executeGetLoginCheckUser(loginCheckUserDataRequest: LoginCheckUserDataRequest): Flow<Result<LoginCheckUser>> {
        return repository.getLoginCheckUser(loginCheckUserDataRequest)
    }

    suspend fun executeLoginVerifyOTP(loginSendOTPDataRequest: LoginSendOTPDataRequest): Flow<Result<LoginVerify>> {
        return repository.getLoginVerifyOTP(loginSendOTPDataRequest)
    }

    suspend fun executeLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest): Flow<Result<LoginRefreshUserDetails>> {
        return repository.getLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest)
    }
}
