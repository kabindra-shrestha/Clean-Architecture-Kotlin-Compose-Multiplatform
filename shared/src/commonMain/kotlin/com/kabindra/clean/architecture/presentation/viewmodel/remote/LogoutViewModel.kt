package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.domain.entity.Logout
import com.kabindra.clean.architecture.domain.usecase.remote.LogoutUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogoutViewModel(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    private val _logoutState =
        MutableStateFlow<Result<Logout>>(Result.Initial)
    val logoutState: StateFlow<Result<Logout>> get() = _logoutState

    fun getLogout() {
        viewModelScope.launch {
            logoutUseCase.executeGetLogout().collect { result ->
                _logoutState.value = result
            }
        }
    }

    fun resetStates() {
        _logoutState.value = Result.Initial
    }
}