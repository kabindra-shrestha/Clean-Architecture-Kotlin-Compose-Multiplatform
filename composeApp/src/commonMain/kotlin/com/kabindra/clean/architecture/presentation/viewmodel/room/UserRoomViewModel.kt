package com.kabindra.clean.architecture.presentation.viewmodel.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.domain.usecase.room.UserRoomUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserRoomViewModel(private val userRoomUseCase: UserRoomUseCase) :
    ViewModel() {
    private val _userState =
        MutableStateFlow<Result<User>>(Result.Initial)
    val userState: StateFlow<Result<User>> get() = _userState

    fun getUser() {
        viewModelScope.launch {
            userRoomUseCase.executeGetUser().collect { result ->
                _userState.value = result
            }
        }
    }

    fun resetStates() {
        _userState.value = Result.Initial
    }
}
