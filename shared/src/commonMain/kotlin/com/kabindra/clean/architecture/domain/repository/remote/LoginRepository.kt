package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.data.request.LoginSendOTPDataRequest
import com.kabindra.clean.architecture.domain.entity.LoginCheckUser
import com.kabindra.clean.architecture.domain.entity.LoginRefreshUserDetails
import com.kabindra.clean.architecture.domain.entity.LoginVerify
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun getLoginCheckUser(loginCheckUserDataRequest: LoginCheckUserDataRequest): Flow<Result<LoginCheckUser>>
    suspend fun getLoginVerifyOTP(loginSendOTPDataRequest: LoginSendOTPDataRequest): Flow<Result<LoginVerify>>
    suspend fun getLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest): Flow<Result<LoginRefreshUserDetails>>
}