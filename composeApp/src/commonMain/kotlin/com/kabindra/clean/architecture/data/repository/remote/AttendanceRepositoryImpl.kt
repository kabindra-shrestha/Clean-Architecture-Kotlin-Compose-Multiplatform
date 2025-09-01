package com.kabindra.clean.architecture.data.repository.remote

import com.kabindra.clean.architecture.data.model.AttendanceDTO
import com.kabindra.clean.architecture.data.model.AttendanceFilterDTO
import com.kabindra.clean.architecture.data.model.RecordedTimeDTO
import com.kabindra.clean.architecture.data.model.toDomain
import com.kabindra.clean.architecture.data.request.AttendanceDataRequest
import com.kabindra.clean.architecture.data.request.RecordedTimeRequest
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.domain.entity.Attendance
import com.kabindra.clean.architecture.domain.entity.AttendanceFilter
import com.kabindra.clean.architecture.domain.entity.RecordedTime
import com.kabindra.clean.architecture.domain.repository.remote.AttendanceRepository
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.ktor.Result
import com.kabindra.clean.architecture.utils.ktor.ResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AttendanceRepositoryImpl(
    private val apiDataSource: ApiDataSource,
) : AttendanceRepository {

    override suspend fun getAttendance(attendanceDataRequest: AttendanceDataRequest): Flow<Result<Attendance>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getAttendance(attendanceDataRequest)
                if (response.status.isSuccess()) {
                    val responses: AttendanceDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
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

    override suspend fun getAttendanceFilter(): Flow<Result<AttendanceFilter>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getAttendanceFilter()
                if (response.status.isSuccess()) {
                    val responses: AttendanceFilterDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
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

    override suspend fun getRecordedTime(recordedTimeRequest: RecordedTimeRequest): Flow<Result<RecordedTime>> =
        flow {
            emit(Result.Loading)
            try {
                val response: HttpResponse =
                    apiDataSource.getRecordedTime(recordedTimeRequest)
                if (response.status.isSuccess()) {
                    val responses: RecordedTimeDTO = response.body()

                    if (getStatus<Status>(responses.status)) {
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


