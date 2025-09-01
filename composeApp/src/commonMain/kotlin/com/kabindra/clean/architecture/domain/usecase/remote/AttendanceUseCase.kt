package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.data.request.AttendanceDataRequest
import com.kabindra.clean.architecture.data.request.RecordedTimeRequest
import com.kabindra.clean.architecture.domain.entity.Attendance
import com.kabindra.clean.architecture.domain.entity.AttendanceFilter
import com.kabindra.clean.architecture.domain.entity.RecordedTime
import com.kabindra.clean.architecture.domain.repository.remote.AttendanceRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class AttendanceUseCase(private val repository: AttendanceRepository) {
    suspend fun executeGetAttendance(attendanceDataRequest: AttendanceDataRequest): Flow<Result<Attendance>> {
        return repository.getAttendance(attendanceDataRequest)
    }

    suspend fun executeGetAttendanceFilter(): Flow<Result<AttendanceFilter>> {
        return repository.getAttendanceFilter()
    }

    suspend fun executeRecordedTime(recordedTimeRequest: RecordedTimeRequest): Flow<Result<RecordedTime>> {
        return repository.getRecordedTime(recordedTimeRequest)
    }
}

