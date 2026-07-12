package com.kabindra.clean.architecture.presentation.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.LoginRefreshUserDetailsDataRequest
import com.kabindra.clean.architecture.domain.usecase.remote.LoginUseCase
import com.kabindra.clean.architecture.domain.usecase.room.AuthenticationRoomUseCase
import com.kabindra.clean.architecture.domain.usecase.room.UserRoomUseCase
import com.kabindra.clean.architecture.utils.constants.AlertType
import com.kabindra.clean.architecture.utils.constants.MessageType
import com.kabindra.clean.architecture.utils.constants.ResponseType
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashViewModel(
    private val loginUseCase: LoginUseCase,
    private val authenticationRoomUseCase: AuthenticationRoomUseCase,
    private val userRoomUseCase: UserRoomUseCase
) : ViewModel() {
    private val _splashState = MutableStateFlow(SplashState())

    val splashState = _splashState
        .asStateFlow()
        .onStart { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SplashState()
        )

    private val _splashEvent = Channel<SplashEvent>()
    val splashEvent = _splashEvent.receiveAsFlow()

    fun onAction(action: SplashAction) {
        when (action) {
            is SplashAction.GetLoginRefreshUserDetails -> {
                println("SplashAction GetLoginRefreshUserDetails")
                getLoginRefreshUserDetails(action.loginRefreshUserDetailsDataRequest)
            }

            is SplashAction.GetIsLogged -> {
                println("SplashAction GetIsLogged")
                getIsLogged()
            }

            is SplashAction.GetUser -> {
                println("SplashAction GetUser")
                getUser()
            }

            is SplashAction.OnNavigateLogin -> {
                println("SplashAction OnNavigateLogin")
                _splashEvent.trySend(SplashEvent.OnNavigateLogin)
            }

            is SplashAction.OnNavigateDashboard -> {
                println("SplashAction OnNavigateDashboard")
                _splashEvent.trySend(SplashEvent.OnNavigateDashboard)
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
                                    loginRefreshUserDetails = result.data
                                )

                            _splashEvent.send(
                                SplashEvent.ShowMessage(
                                    messageType = MessageType.Success,
                                    responseType = ResponseType.None,
                                    alertType = AlertType.None,
                                    message = result.data.message
                                )
                            )
                        }

                        is Result.Error -> {
                            _splashState.value =
                                _splashState.value.copy(
                                    isLoading = false,
                                    isLogged = null,
                                    user = null
                                )

                            _splashEvent.send(
                                SplashEvent.ShowMessage(
                                    messageType = MessageType.Error,
                                    responseType = ResponseType.None,
                                    alertType = AlertType.Dialog,
                                    title = result.error.title ?: "",
                                    message = result.error.message,
                                    statusCode = result.error.statusCode
                                )
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
                                isLogged = result.data
                            )

                        _splashEvent.send(
                            SplashEvent.ShowMessage(
                                messageType = MessageType.Success,
                                responseType = ResponseType.None,
                                alertType = AlertType.None,
                                message = ""
                            )
                        )
                    }

                    is Result.Error -> {
                        _splashState.value =
                            _splashState.value.copy(
                                isLoading = false,
                            )

                        _splashEvent.send(
                            SplashEvent.ShowMessage(
                                messageType = MessageType.Error,
                                responseType = ResponseType.None,
                                alertType = AlertType.Dialog,
                                title = result.error.title ?: "",
                                message = result.error.message,
                                statusCode = result.error.statusCode
                            )
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
                                user = result.data
                            )

                        _splashEvent.send(
                            SplashEvent.ShowMessage(
                                messageType = MessageType.Success,
                                responseType = ResponseType.None,
                                alertType = AlertType.None,
                                message = ""
                            )
                        )
                    }

                    is Result.Error -> {
                        _splashState.value =
                            _splashState.value.copy(
                                isLoading = false,
                            )

                        _splashEvent.send(
                            SplashEvent.ShowMessage(
                                messageType = MessageType.Error,
                                responseType = ResponseType.None,
                                alertType = AlertType.Dialog,
                                title = result.error.title ?: "",
                                message = result.error.message,
                                statusCode = result.error.statusCode
                            )
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