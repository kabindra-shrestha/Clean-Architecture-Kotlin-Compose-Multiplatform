package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.MPINSetDataRequest
import com.kabindra.clean.architecture.data.request.MPINVerifyDataRequest
import com.kabindra.clean.architecture.domain.entity.MPINSet
import com.kabindra.clean.architecture.domain.entity.MPINVerify
import com.kabindra.clean.architecture.domain.usecase.remote.MPINUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MPINViewModel(private val mPINUseCase: MPINUseCase) : ViewModel() {
    private val _mPINSetState =
        MutableStateFlow<Result<MPINSet>>(Result.Initial)
    val mPINSetState: StateFlow<Result<MPINSet>> get() = _mPINSetState

    private val _mPINVerifyState =
        MutableStateFlow<Result<MPINVerify>>(Result.Initial)
    val mPINVerifyState: StateFlow<Result<MPINVerify>> get() = _mPINVerifyState

    fun getMPINSet(mPINSetDataRequest: MPINSetDataRequest) {
        viewModelScope.launch {
            mPINUseCase.executeGetMPINSet(mPINSetDataRequest).collect { result ->
                _mPINSetState.value = result
            }
        }
    }

    fun getMPINVerify(mPINVerifyDataRequest: MPINVerifyDataRequest) {
        viewModelScope.launch {
            mPINUseCase.executeGetMPINVerify(mPINVerifyDataRequest)
                .collect { result ->
                    _mPINVerifyState.value = result
                }
        }
    }

    fun resetStates() {
        _mPINSetState.value = Result.Initial
    }
}