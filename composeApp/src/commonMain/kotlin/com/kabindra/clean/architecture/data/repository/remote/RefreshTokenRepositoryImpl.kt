package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.ApiTokenDTO
import com.kabindra.clean.architecture.data.model.RefreshTokenDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.request.RefreshTokenDataRequest
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.di.TokenProvider
import com.kabindra.clean.architecture.domain.entity.RefreshToken
import com.kabindra.clean.architecture.domain.repository.remote.RefreshTokenRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RefreshTokenRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val appDatabase: AppDatabase,
    private val tokenprovider: TokenProvider
) : RefreshTokenRepository {

    override suspend fun getRefreshToken(refreshTokenDataRequest: RefreshTokenDataRequest): Flow<Result<RefreshToken>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse = apiDataSource.getRefreshToken(refreshTokenDataRequest)
                if (response.status.isSuccess()) {
                    val responses: RefreshTokenDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
                        appDatabase.apiTokenDao.deleteAll()

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