package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.data.request.AttendanceDataRequest
import com.kabindra.clean.architecture.data.request.RecordedTimeRequest
import com.kabindra.clean.architecture.domain.entity.Attendance
import com.kabindra.clean.architecture.domain.entity.AttendanceFilter
import com.kabindra.clean.architecture.domain.entity.RecordedTime
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    suspend fun getAttendance(attendanceDataRequest: AttendanceDataRequest): Flow<Result<Attendance>>
    suspend fun getAttendanceFilter(): Flow<Result<AttendanceFilter>>
    suspend fun getRecordedTime(recordedTimeRequest: RecordedTimeRequest): Flow<Result<RecordedTime>>
}
