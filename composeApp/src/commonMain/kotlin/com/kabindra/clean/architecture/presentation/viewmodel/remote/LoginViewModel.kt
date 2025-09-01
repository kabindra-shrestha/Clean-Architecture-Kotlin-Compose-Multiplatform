package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.data.request.LoginSendOTPDataRequest
import com.kabindra.clean.architecture.domain.entity.LoginCheckUser
import com.kabindra.clean.architecture.domain.entity.LoginRefreshUserDetails
import com.kabindra.clean.architecture.domain.entity.LoginVerify
import com.kabindra.clean.architecture.domain.usecase.remote.LoginUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginCheckUserState =
        MutableStateFlow<Result<LoginCheckUser>>(Result.Initial)
    private val _loginSendOTPState =
        MutableStateFlow<Result<LoginVerify>>(Result.Initial)
    private val _loginRefreshUserDetailsState =
        MutableStateFlow<Result<LoginRefreshUserDetails>>(Result.Initial)

    val loginCheckUserState: StateFlow<Result<LoginCheckUser>> get() = _loginCheckUserState
    val loginSendOTPState: StateFlow<Result<LoginVerify>> get() = _loginSendOTPState
    val loginRefreshUserDetailsState: StateFlow<Result<LoginRefreshUserDetails>> get() = _loginRefreshUserDetailsState

    fun getLoginCheckUser(loginCheckUserDataRequest: LoginCheckUserDataRequest) {
        viewModelScope.launch {
            loginUseCase.executeGetLoginCheckUser(loginCheckUserDataRequest).collect { result ->
                _loginCheckUserState.value = result
            }
        }
    }

    fun getLoginVerifyOTP(loginSendOTPRequest: LoginSendOTPDataRequest) {
        viewModelScope.launch {
            loginUseCase.executeLoginVerifyOTP(loginSendOTPRequest).collect { result ->
                _loginSendOTPState.value = result
            }
        }
    }

    fun getLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest) {
        viewModelScope.launch {
            loginUseCase.executeLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest)
                .collect { result ->
                    _loginRefreshUserDetailsState.value = result
                }
        }
    }

    fun resetStates() {
        _loginCheckUserState.value = Result.Initial
        _loginSendOTPState.value = Result.Initial
        _loginRefreshUserDetailsState.value = Result.Initial
    }
}