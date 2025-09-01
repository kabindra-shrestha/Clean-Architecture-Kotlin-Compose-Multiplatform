package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.ApplyTimeRequestDataRequest
import com.kabindra.clean.architecture.data.request.TimeRequestUpdateDataRequest
import com.kabindra.clean.architecture.domain.entity.ApplyTime
import com.kabindra.clean.architecture.domain.entity.EditTimeRequest
import com.kabindra.clean.architecture.domain.entity.TimeRequestVerifier
import com.kabindra.clean.architecture.domain.entity.UpdateTimeRequest
import com.kabindra.clean.architecture.domain.usecase.remote.TimeUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimeViewModel(private val timeUseCase: TimeUseCase) : ViewModel() {

    private val _timeRequestVerifierState =
        MutableStateFlow<Result<TimeRequestVerifier>>(Result.Initial)
    val timeRequestVerifierState: StateFlow<Result<TimeRequestVerifier>> get() = _timeRequestVerifierState

    private val _applyTimeRequestState =
        MutableStateFlow<Result<ApplyTime>>(Result.Initial)
    val applyTimeRequestState: StateFlow<Result<ApplyTime>> get() = _applyTimeRequestState


    private val _editTimeRequestState =
        MutableStateFlow<Result<EditTimeRequest>>(Result.Initial)
    val editTimeRequestState: StateFlow<Result<EditTimeRequest>> get() = _editTimeRequestState


    private val _updateTimeRequestState =
        MutableStateFlow<Result<UpdateTimeRequest>>(Result.Initial)
    val updateTimeRequestState: StateFlow<Result<UpdateTimeRequest>> get() = _updateTimeRequestState


    fun getTimeRequestVerifiers() {
        viewModelScope.launch {
            timeUseCase.executeGetTimeRequestVerifiers().collect { result ->
                _timeRequestVerifierState.value = result
            }
        }
    }

    fun getApplyTimeRequest(applyTimeRequestDataRequest: ApplyTimeRequestDataRequest) {
        viewModelScope.launch {
            timeUseCase.executeGetApplyTimeRequest(applyTimeRequestDataRequest)
                .collect { result ->
                    _applyTimeRequestState.value = result
                }
        }
    }

    fun getTimeRequestEdit(ticket_id: String) {
        viewModelScope.launch {
            timeUseCase.executeGetTimeRequestEdit(ticket_id).collect { result ->
                _editTimeRequestState.value = result
            }
        }
    }

    fun getUpdateTimeRequest(timeRequestUpdateDataRequest: TimeRequestUpdateDataRequest) {
        viewModelScope.launch {
            timeUseCase.executeGetTimeRequestUpdate(timeRequestUpdateDataRequest)
                .collect { result ->
                    _updateTimeRequestState.value = result
                }
        }
    }

    fun resetStates() {
        _timeRequestVerifierState.value = Result.Initial
        _applyTimeRequestState.value = Result.Initial
        _editTimeRequestState.value = Result.Initial
        _updateTimeRequestState.value = Result.Initial
    }

}