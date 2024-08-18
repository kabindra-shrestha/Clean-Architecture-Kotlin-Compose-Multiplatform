package com.kabindra.architecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.domain.usecase.remote.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.kabindra.architecture.utils.NetworkResult

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {
    private val _newsState = MutableStateFlow<NetworkResult<News>>(NetworkResult.Initial)
    val newsState: StateFlow<NetworkResult<News>> get() = _newsState

    fun loadNews() {
        viewModelScope.launch {
            getNewsUseCase.execute().collect { result ->
                _newsState.value = result
            }
        }
    }
}
