package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.ApplyLeaveRequestDataRequest
import com.kabindra.clean.architecture.data.request.LeaveRequestUpdateDataRequest
import com.kabindra.clean.architecture.domain.entity.ApplyLeave
import com.kabindra.clean.architecture.domain.entity.AssignedLeaveType
import com.kabindra.clean.architecture.domain.entity.EditLeaveRequest
import com.kabindra.clean.architecture.domain.entity.LeaveDetails
import com.kabindra.clean.architecture.domain.entity.LeaveRequestVerifier
import com.kabindra.clean.architecture.domain.entity.UpdateLeaveRequest
import com.kabindra.clean.architecture.domain.usecase.remote.LeaveUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LeaveViewModel(private val leaveUseCase: LeaveUseCase) : ViewModel() {

    private val _applyLeaveRequestState =
        MutableStateFlow<Result<ApplyLeave>>(Result.Initial)
    val applyLeaveRequestState: StateFlow<Result<ApplyLeave>> get() = _applyLeaveRequestState

    private val _assignedLeaveTypeState =
        MutableStateFlow<Result<AssignedLeaveType>>(Result.Initial)
    val assignedLeaveTypeState: StateFlow<Result<AssignedLeaveType>> get() = _assignedLeaveTypeState

    private val _leaveRequestVerifierState =
        MutableStateFlow<Result<LeaveRequestVerifier>>(Result.Initial)
    val leaveRequestVerifierState: StateFlow<Result<LeaveRequestVerifier>> get() = _leaveRequestVerifierState

    private val _leaveDetailsState =
        MutableStateFlow<Result<LeaveDetails>>(Result.Initial)
    val leaveDetailsState: StateFlow<Result<LeaveDetails>> get() = _leaveDetailsState

    private val _leaveEditState =
        MutableStateFlow<Result<EditLeaveRequest>>(Result.Initial)
    val leaveEditState: StateFlow<Result<EditLeaveRequest>> get() = _leaveEditState

    private val _leaveUpdateState =
        MutableStateFlow<Result<UpdateLeaveRequest>>(Result.Initial)
    val leaveUpdateState: StateFlow<Result<UpdateLeaveRequest>> get() = _leaveUpdateState

    fun getApplyLeaveRequest(applyLeaveRequestDataRequest: ApplyLeaveRequestDataRequest) {
        viewModelScope.launch {
            leaveUseCase.executeGetApplyLeaveRequest(applyLeaveRequestDataRequest)
                .collect { result ->
                    _applyLeaveRequestState.value = result
                }
        }
    }

    fun getLeaveRequestVerifiers() {
        viewModelScope.launch {
            leaveUseCase.executeGetLeaveRequestVerifiers().collect { result ->
                _leaveRequestVerifierState.value = result
            }
        }
    }

    fun getLeaveDetails() {
        viewModelScope.launch {
            leaveUseCase.executeGetLeaveDetails().collect { result ->
                _leaveDetailsState.value = result
            }
        }
    }

    fun getLeaveRequestEdit(ticket_id: String) {
        viewModelScope.launch {
            leaveUseCase.executeGetLeaveRequestEdit(ticket_id).collect { result ->
                _leaveEditState.value = result
            }
        }
    }

    fun getLeaveRequestUpdate(applyTimeRequestUpdateDataRequest: LeaveRequestUpdateDataRequest) {
        viewModelScope.launch {
            leaveUseCase.executeGetLeaveUpdate(applyTimeRequestUpdateDataRequest)
                .collect { result ->
                    _leaveUpdateState.value = result
                }
        }
    }

    fun resetStates() {
        _applyLeaveRequestState.value = Result.Initial
        _assignedLeaveTypeState.value = Result.Initial
        _leaveRequestVerifierState.value = Result.Initial
        _leaveDetailsState.value = Result.Initial
        _leaveEditState.value = Result.Initial
        _leaveUpdateState.value = Result.Initial
    }

}