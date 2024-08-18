package com.kabindra.architecture.utils

sealed class NetworkResult<out T> {
    data object Initial : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
}
