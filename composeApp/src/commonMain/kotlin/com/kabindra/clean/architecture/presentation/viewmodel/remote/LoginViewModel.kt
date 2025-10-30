package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.LoginCheckUserDataRequest
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.data.request.LoginSendOTPDataRequest
import com.kabindra.clean.architecture.domain.usecase.remote.LoginUseCase
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginCheckUserState = MutableStateFlow(LoginState())
    private val _loginSendOTPState = MutableStateFlow(LoginState())
    private val _loginRefreshUserDetailsState = MutableStateFlow(LoginState())

    val loginCheckUserState = _loginCheckUserState
        .onStart { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LoginState()
        )
    val loginSendOTPState = _loginSendOTPState
        .onStart { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LoginState()
        )
    val loginRefreshUserDetailsState = _loginRefreshUserDetailsState
        .onStart { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            LoginState()
        )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.GetLoginCheckUser -> {
                getLoginCheckUser(event.loginCheckUserDataRequest)
            }

            is LoginEvent.GetLoginVerifyOTP -> {
                getLoginVerifyOTP(event.loginSendOTPRequest)
            }

            is LoginEvent.GetLoginRefreshUserDetails -> {
                getLoginRefreshUserDetails(event.loginRefreshUserDetailsDataRequest)
            }
        }
    }

    fun getLoginCheckUser(loginCheckUserDataRequest: LoginCheckUserDataRequest) {
        viewModelScope.launch {
            loginUseCase.executeGetLoginCheckUser(loginCheckUserDataRequest).collect { result ->
                when (result) {
                    is Result.Initial -> Unit

                    is Result.Loading -> {
                        _loginCheckUserState.value = _loginCheckUserState.value.copy(
                            isLoading = true
                        )
                    }

                    is Result.Success -> {
                        _loginCheckUserState.value = _loginCheckUserState.value.copy(
                            isLoading = false,
                            loginCheckUser = result.data
                        )
                    }

                    is Result.Error -> {
                        _loginCheckUserState.value = _loginCheckUserState.value.copy(
                            isError = true,
                            errorType = ResponseType.None,
                            errorStatusCode = result.error.statusCode,
                            errorTitle = "",
                            errorMessage = result.error.message
                        )
                    }
                }
            }
        }
    }

    fun getLoginVerifyOTP(loginSendOTPRequest: LoginSendOTPDataRequest) {
        viewModelScope.launch {
            loginUseCase.executeLoginVerifyOTP(loginSendOTPRequest).collect { result ->
                when (result) {
                    is Result.Initial -> Unit
                    is Result.Loading -> {
                        _loginSendOTPState.value = _loginSendOTPState.value.copy(
                            isLoading = true
                        )
                    }

                    is Result.Success -> {
                        _loginSendOTPState.value = _loginSendOTPState.value.copy(
                            isLoading = false,
                            loginVerify = result.data
                        )
                    }

                    is Result.Error -> {
                        _loginSendOTPState.value = _loginSendOTPState.value.copy(
                            isError = true,
                            errorType = ResponseType.None,
                            errorStatusCode = result.error.statusCode,
                            errorTitle = "",
                            errorMessage = result.error.message
                        )
                    }
                }
            }
        }
    }

    fun getLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest: LoginRefreshUserDetailsDataRequest) {
        viewModelScope.launch {
            loginUseCase.executeLoginRefreshUserDetails(loginRefreshUserDetailsDataRequest)
                .collect { result ->
                    when (result) {
                        is Result.Initial -> Unit
                        is Result.Loading -> {
                            _loginRefreshUserDetailsState.value =
                                _loginRefreshUserDetailsState.value.copy(
                                    isLoading = true
                                )
                        }

                        is Result.Success -> {
                            _loginRefreshUserDetailsState.value =
                                _loginRefreshUserDetailsState.value.copy(
                                    isLoading = false,
                                    loginRefreshUserDetails = result.data
                                )
                        }

                        is Result.Error -> {
                            _loginRefreshUserDetailsState.value =
                                _loginRefreshUserDetailsState.value.copy(
                                    isError = true,
                                    errorType = ResponseType.None,
                                    errorStatusCode = result.error.statusCode,
                                    errorTitle = "",
                                    errorMessage = result.error.message
                                )
                        }
                    }
                }
        }
    }

    fun resetStates() {
        _loginCheckUserState.value = LoginState()
        _loginSendOTPState.value = LoginState()
        _loginRefreshUserDetailsState.value = LoginState()
    }
}