package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.domain.entity.Profile
import com.kabindra.clean.architecture.domain.usecase.remote.ProfileUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {
    private val _profileState =
        MutableStateFlow<Result<Profile>>(Result.Initial)

    val profileState: StateFlow<Result<Profile>> get() = _profileState

    fun getProfile() {
        viewModelScope.launch {
            profileUseCase.executeGetProfile().collect { result ->
                _profileState.value = result
            }
        }
    }
}