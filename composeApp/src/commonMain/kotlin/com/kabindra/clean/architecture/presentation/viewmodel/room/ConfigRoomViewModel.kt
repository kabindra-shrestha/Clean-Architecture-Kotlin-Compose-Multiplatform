package com.kabindra.clean.architecture.presentation.viewmodel.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.domain.entity.Config
import com.kabindra.clean.architecture.domain.usecase.room.ConfigRoomUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfigRoomViewModel(private val configRoomUseCase: ConfigRoomUseCase) : ViewModel() {
    private val _configBaseUrlState =
        MutableStateFlow<Result<String>>(Result.Initial)
    private val _configBaseUrlSaveState =
        MutableStateFlow<Result<Boolean>>(Result.Initial)
    val configBaseUrlState: StateFlow<Result<String>> get() = _configBaseUrlState
    val configBaseUrlSaveState: StateFlow<Result<Boolean>> get() = _configBaseUrlSaveState

    fun getConfigBaseUrl() {
        viewModelScope.launch {
            configRoomUseCase.executeGetConfigBaseUrl().collect { result ->
                _configBaseUrlState.value = result
            }
        }
    }

    fun getConfigSaveBaseUrl(config: Config) {
        viewModelScope.launch {
            configRoomUseCase.executeGetConfigSaveBaseUrl(config).collect { result ->
                _configBaseUrlSaveState.value = result
            }
        }
    }

    fun resetStates() {
        _configBaseUrlState.value = Result.Initial
        _configBaseUrlSaveState.value = Result.Initial
    }
}