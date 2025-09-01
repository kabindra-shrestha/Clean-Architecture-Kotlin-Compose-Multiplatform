package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.RefreshTokenDataRequest
import com.kabindra.clean.architecture.domain.entity.RefreshToken
import com.kabindra.clean.architecture.domain.usecase.remote.RefreshTokenUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RefreshTokenViewModel(private val refreshTokenUseCase: RefreshTokenUseCase) : ViewModel() {
    private val _refreshTokenState =
        MutableStateFlow<Result<RefreshToken>>(Result.Initial)

    val refreshTokenState: StateFlow<Result<RefreshToken>> get() = _refreshTokenState

    fun getRefreshToken(refreshTokenDataRequest: RefreshTokenDataRequest) {
        viewModelScope.launch {
            refreshTokenUseCase.executeGetRefreshToken(refreshTokenDataRequest).collect { result ->
                _refreshTokenState.value = result
            }
        }
    }
}