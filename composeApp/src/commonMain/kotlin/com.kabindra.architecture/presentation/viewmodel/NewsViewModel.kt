package com.kabindra.architecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.domain.usecase.remote.GetNewsUseCase
import com.kabindra.architecture.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {
    private val _newsState = MutableStateFlow<Result<News>>(Result.Initial)
    val newsState: StateFlow<Result<News>> get() = _newsState

    fun loadNews() {
        viewModelScope.launch {
            getNewsUseCase.execute().collect { result ->
                _newsState.value = result
            }
        }
    }
}
