package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.domain.usecase.remote.LoginUseCase
import com.kabindra.clean.architecture.domain.usecase.room.AuthenticationRoomUseCase
import com.kabindra.clean.architecture.domain.usecase.room.UserRoomUseCase
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashViewModel(
    private val loginUseCase: LoginUseCase,
    private val authenticationRoomUseCase: AuthenticationRoomUseCase,
    private val userRoomUseCase: UserRoomUseCase
) : ViewModel() {
    private val _splashState = MutableStateFlow(SplashState())

    val splashState = _splashState
        .onStart { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SplashState()
        )

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.GetLoginRefreshUserDetails -> {
                getLoginRefreshUserDetails(event.loginRefreshUserDetailsDataRequest)
            }

            is SplashEvent.GetIsLogged -> {
                getIsLogged()
            }

            is SplashEvent.GetUser -> {
                getUser()
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
                            _splashState.value =
                                _splashState.value.copy(
                                    isLoading = true
                                )
                        }

                        is Result.Success -> {
                            _splashState.value =
                                _splashState.value.copy(
                                    isLoading = false,
                                    isSuccess = true,
                                    successType = ResponseType.None,
                                    successMessage = "",
                                    loginRefreshUserDetails = result.data
                                )
                        }

                        is Result.Error -> {
                            _splashState.value =
                                _splashState.value.copy(
                                    isLoading = false,
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

    fun getIsLogged() {
        viewModelScope.launch {
            authenticationRoomUseCase.executeGetIsLoggedApi().collect { result ->
                when (result) {
                    is Result.Initial -> Unit
                    is Result.Loading -> {
                        _splashState.value =
                            _splashState.value.copy(
                                isLoading = true
                            )
                    }

                    is Result.Success -> {
                        _splashState.value =
                            _splashState.value.copy(
                                isLoading = false,
                                isSuccess = true,
                                successType = ResponseType.None,
                                successMessage = "",
                                isLogged = result.data
                            )
                    }

                    is Result.Error -> {
                        _splashState.value =
                            _splashState.value.copy(
                                isLoading = false,
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

    fun getUser() {
        viewModelScope.launch {
            userRoomUseCase.executeGetUser().collect { result ->
                when (result) {
                    is Result.Initial -> Unit
                    is Result.Loading -> {
                        _splashState.value =
                            _splashState.value.copy(
                                isLoading = true
                            )
                    }

                    is Result.Success -> {
                        _splashState.value =
                            _splashState.value.copy(
                                isLoading = false,
                                isSuccess = true,
                                successType = ResponseType.None,
                                successMessage = "",
                                user = result.data
                            )
                    }

                    is Result.Error -> {
                        _splashState.value =
                            _splashState.value.copy(
                                isLoading = false,
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
        _splashState.value = SplashState()
    }
}