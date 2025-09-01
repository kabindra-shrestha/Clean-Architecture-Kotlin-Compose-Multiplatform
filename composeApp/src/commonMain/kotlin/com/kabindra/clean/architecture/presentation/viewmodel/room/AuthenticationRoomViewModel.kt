package com.kabindra.clean.architecture.presentation.viewmodel.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.domain.usecase.room.AuthenticationRoomUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthenticationRoomViewModel(private val authenticationRoomUseCase: AuthenticationRoomUseCase) :
    ViewModel() {
    private val _authenticationLoggedApiState =
        MutableStateFlow<Result<Boolean>>(Result.Initial)
    private val _authenticationLoggedApiTokenState =
        MutableStateFlow<Result<String>>(Result.Initial)
    private val _authenticationLogoutState =
        MutableStateFlow<Result<Boolean>>(Result.Initial)
    val authenticationLoggedApiState: StateFlow<Result<Boolean>> get() = _authenticationLoggedApiState
    val authenticationLoggedApiTokenState: StateFlow<Result<String>> get() = _authenticationLoggedApiTokenState
    val authenticationLogoutState: StateFlow<Result<Boolean>> get() = _authenticationLogoutState

    fun getIsLogged() {
        viewModelScope.launch {
            authenticationRoomUseCase.executeGetIsLoggedApi().collect { result ->
                _authenticationLoggedApiState.value = result
            }
        }
    }

    fun getIsLoggedApiToken() {
        viewModelScope.launch {
            authenticationRoomUseCase.executeGetIsLoggedApiToken().collect { result ->
                _authenticationLoggedApiTokenState.value = result
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authenticationRoomUseCase.executeLogout().collect { result ->
                _authenticationLogoutState.value = result
            }
        }
    }

    fun resetStates() {
        _authenticationLoggedApiState.value = Result.Initial
        _authenticationLoggedApiTokenState.value = Result.Initial
    }
}
