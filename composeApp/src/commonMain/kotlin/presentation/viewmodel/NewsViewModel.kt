package presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.entity.News
import domain.usecase.remote.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import utils.NetworkResult

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {
    private val _newsState = MutableStateFlow<NetworkResult<News>>(NetworkResult.Loading)
    val newsState: StateFlow<NetworkResult<News>> get() = _newsState

    fun loadNews() {
        viewModelScope.launch {
            getNewsUseCase.execute().collect { result ->
                _newsState.value = result
            }
        }
    }
}
