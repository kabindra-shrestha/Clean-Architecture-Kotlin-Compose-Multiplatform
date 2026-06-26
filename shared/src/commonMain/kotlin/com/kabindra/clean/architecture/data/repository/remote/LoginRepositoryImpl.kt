package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.ApiTokenDTO
import com.kabindra.clean.architecture.data.model.LoginCheckUserDTO
import com.kabindra.clean.architecture.data.model.LoginRefreshUserDetailsDTO
import com.kabindra.clean.architecture.data.model.LoginVerifyDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.data.request.LoginSendOTPDataRequest
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.di.TokenProvider
import com.kabindra.clean.architecture.domain.entity.LoginCheckUser
import com.kabindra.clean.architecture.domain.entity.LoginRefreshUserDetails
import com.kabindra.clean.architecture.domain.entity.LoginVerify
import com.kabindra.clean.architecture.domain.repository.remote.LoginRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val appDatabase: AppDatabase,
    private val tokenprovider: TokenProvider
) : LoginRepository {

    override suspend fun getLoginCheckUser(loginCheckUserDataRequest: LoginCheckUserDataRequest): Flow<Result<LoginCheckUser>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getLoginCheckUser(loginCheckUserDataRequest)
                if (response.status.isSuccess()) {
                    val responses: LoginCheckUserDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        appDatabase.apiTokenDao.deleteAll()
                        appDatabase.userDao.deleteAll()

                        responses.response.takeIf { !it?.token.isNullOrEmpty() && !it?.refresh_token.isNullOrEmpty() }
                            ?.let {
                                appDatabase.apiTokenDao.add(
                                    ApiTokenDTO(
                                        it.token!!,
                                        it.refresh_token!!
                                    )
                                )

                                tokenprovider.updateTokens(
                                    it.token,
                                    it.refresh_token!!
                                )
                            }

                        responses.response.takeIf { it?.user_details != null }
                            ?.let {
                                appDatabase.userDao.add(it.user_details!!)
                            }

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

    override suspend fun getLoginVerifyOTP(loginSendOTPDataRequest: LoginSendOTPDataRequest): Flow<Result<LoginVerify>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse = apiDataSource.getLoginSendOTP(loginSendOTPDataRequest)
                if (response.status.isSuccess()) {
                    val responses: LoginVerifyDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        appDatabase.apiTokenDao.deleteAll()
                        appDatabase.userDao.deleteAll()

                        appDatabase.apiTokenDao.add(
                            ApiTokenDTO(
                                responses.response?.token!!,
                                responses.response.refresh_token!!
                            )
                        )

                        tokenprovider.updateTokens(
                            responses.response.token,
                            responses.response.refresh_token!!
                        )

                        appDatabase.userDao.add(responses.response.user_details!!)

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

    override suspend fun getLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest): Flow<Result<LoginRefreshUserDetails>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest)
                if (response.status.isSuccess()) {
                    val responses: LoginRefreshUserDetailsDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        appDatabase.userDao.deleteAll()

                        appDatabase.userDao.add(responses.response?.user_details!!)

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