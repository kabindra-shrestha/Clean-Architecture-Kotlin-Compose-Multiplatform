package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.AttendanceDataRequest
import com.kabindra.clean.architecture.data.request.RecordedTimeRequest
import com.kabindra.clean.architecture.domain.entity.Attendance
import com.kabindra.clean.architecture.domain.entity.AttendanceFilter
import com.kabindra.clean.architecture.domain.entity.RecordedTime
import com.kabindra.clean.architecture.domain.usecase.remote.AttendanceUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AttendanceViewModel(private val attendanceUseCase: AttendanceUseCase) : ViewModel() {
    private val _attendanceState =
        MutableStateFlow<Result<Attendance>>(Result.Initial)
    val attendanceState: StateFlow<Result<Attendance>> get() = _attendanceState

    private val _attendanceFilterState =
        MutableStateFlow<Result<AttendanceFilter>>(Result.Initial)
    val attendanceFilterState: StateFlow<Result<AttendanceFilter>> get() = _attendanceFilterState

    private val _recordedTimeState =
        MutableStateFlow<Result<RecordedTime>>(Result.Initial)
    val recordedTimeState: StateFlow<Result<RecordedTime>> get() = _recordedTimeState

    fun getAttendance(attendanceDataRequest: AttendanceDataRequest) {
        viewModelScope.launch {
            attendanceUseCase.executeGetAttendance(attendanceDataRequest).collect { result ->
                _attendanceState.value = result
            }
        }
    }

    fun getAttendanceFilter() {
        viewModelScope.launch {
            attendanceUseCase.executeGetAttendanceFilter().collect { result ->
                _attendanceFilterState.value = result
            }
        }
    }

    fun getRecordedTime(recordedTimeRequest: RecordedTimeRequest) {
        viewModelScope.launch {
            attendanceUseCase.executeRecordedTime(recordedTimeRequest).collect { result ->
                _recordedTimeState.value = result
            }
        }
    }

    fun resetStates() {
        _attendanceState.value = Result.Initial
        _attendanceFilterState.value = Result.Initial
        _recordedTimeState.value = Result.Initial
    }

}