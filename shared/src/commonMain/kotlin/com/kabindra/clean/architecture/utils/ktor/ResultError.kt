package com.kabindra.clean.architecture.utils.ktor

import androidx.sqlite.SQLiteException
import com.kabindra.clean.architecture.utils.base.ErrorResponse
import com.kabindra.clean.architecture.utils.constants.ConfigValue.Companion.isDebug
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_CONNECT_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_HTTP_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_INTERNAL_SERVER_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_JSON_SYNTAX_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_NUMBER_FORMAT_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_SOCKET_TIMEOUT_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_SQLITE_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_TITLE_NO_INTERNET_CONNECTIVITY
import com.kabindra.clean.architecture.utils.constants.ErrorType.Companion.ERROR_UNKNOWN_HOST_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_CONNECT_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_HTTP_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_INTERNAL_SERVER_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_JSON_SYNTAX_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_NUMBER_FORMAT_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_SOCKET_TIMEOUT_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_SQLITE_EXCEPTION
import com.kabindra.clean.architecture.utils.constants.StatusCode.Companion.STATUS_UNKNOWN_HOST_EXCEPTION
import com.kabindra.clean.architecture.utils.enums.InputFieldIdType
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

object ResultError {

    suspend fun parseError(response: HttpResponse): ErrorResponse {
        println("Error: Error:")
        val json = Json {
            ignoreUnknownKeys = true
        }
        val errorResponse: ErrorResponse

        return try {
            errorResponse = json.decodeFromString<ErrorResponse>(response.body())

            println("Error: ${errorResponse.statusCode}: ${errorResponse.message}")
            errorResponse
        } catch (e: Exception) {
            parseException(e)
        }
    }

    fun parseException(exception: Exception): ErrorResponse {
        println("Error: Exception: ${exception.message.toString()}")
        return handleException(exception)
    }

    private fun handleException(e: Throwable): ErrorResponse {
        val errorResponse = ErrorResponse()

        return when (e) {
            // Ktor HTTP exceptions
            is ClientRequestException -> { // 4xx errors
                errorResponse.status = STATUS_HTTP_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_HTTP_EXCEPTION
                errorResponse.message = if (isDebug) e.message else ERROR_HTTP_EXCEPTION
                errorResponse.title = ERROR_TITLE_NO_INTERNET_CONNECTIVITY
                errorResponse
            }

            is ServerResponseException -> { // 5xx errors
                errorResponse.status = STATUS_INTERNAL_SERVER_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_INTERNAL_SERVER_EXCEPTION
                errorResponse.message =
                    if (isDebug) e.message else ERROR_INTERNAL_SERVER_EXCEPTION
                errorResponse.title = ""
                errorResponse
            }

            is ResponseException -> { // Generic HTTP response errors
                errorResponse.status = STATUS_HTTP_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_HTTP_EXCEPTION
                errorResponse.message = if (isDebug) e.message!! else ERROR_HTTP_EXCEPTION
                errorResponse.title = ERROR_TITLE_NO_INTERNET_CONNECTIVITY
                errorResponse
            }

            // Connection errors
            is TimeoutCancellationException -> { // Request timeout
                errorResponse.status = STATUS_SOCKET_TIMEOUT_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_SOCKET_TIMEOUT_EXCEPTION
                errorResponse.message = if (isDebug) e.message!! else ERROR_SOCKET_TIMEOUT_EXCEPTION
                errorResponse.title = ERROR_TITLE_NO_INTERNET_CONNECTIVITY
                errorResponse
            }

            is UnresolvedAddressException -> { // No internet or DNS issues
                errorResponse.status = STATUS_UNKNOWN_HOST_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_UNKNOWN_HOST_EXCEPTION
                errorResponse.message = if (isDebug) e.message!! else ERROR_UNKNOWN_HOST_EXCEPTION
                errorResponse.title = ERROR_TITLE_NO_INTERNET_CONNECTIVITY
                errorResponse
            }

            is kotlinx.io.IOException -> { // Network failure
                errorResponse.status = STATUS_CONNECT_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_CONNECT_EXCEPTION
                errorResponse.message = if (isDebug) e.message!! else ERROR_CONNECT_EXCEPTION
                errorResponse.title = ERROR_TITLE_NO_INTERNET_CONNECTIVITY
                errorResponse
            }

            // JSON parsing errors
            is SerializationException -> {
                errorResponse.status = STATUS_JSON_SYNTAX_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_JSON_SYNTAX_EXCEPTION
                errorResponse.message = if (isDebug) e.message!! else ERROR_JSON_SYNTAX_EXCEPTION
                errorResponse.title = ""
                errorResponse
            }

            // Number format issues
            is NumberFormatException -> {
                errorResponse.status = STATUS_NUMBER_FORMAT_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_NUMBER_FORMAT_EXCEPTION
                errorResponse.message = if (isDebug) e.message!! else ERROR_NUMBER_FORMAT_EXCEPTION
                errorResponse.title = ""
                errorResponse
            }

            // SQLite errors
            is SQLiteException -> {
                errorResponse.status = STATUS_SQLITE_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_SQLITE_EXCEPTION
                errorResponse.message = if (isDebug) e.message!! else ERROR_SQLITE_EXCEPTION
                errorResponse.title = ""
                errorResponse
            }

            // Cancellation
            is CancellationException -> {
                errorResponse.status = STATUS_INTERNAL_SERVER_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_INTERNAL_SERVER_EXCEPTION
                errorResponse.message =
                    if (isDebug) e.message!! else ERROR_INTERNAL_SERVER_EXCEPTION
                errorResponse.title = ""
                errorResponse
            }

            // Generic error
            else -> {
                errorResponse.status = STATUS_INTERNAL_SERVER_EXCEPTION.toString()
                errorResponse.statusCode = STATUS_INTERNAL_SERVER_EXCEPTION
                errorResponse.message =
                    if (isDebug) e.message!! else ERROR_INTERNAL_SERVER_EXCEPTION
                errorResponse.title = ""
                errorResponse
            }
        }
    }

    fun handleInputFieldErrors(
        errorMessages: Map<String, List<String>>?,
        updateFieldError: (field: InputFieldIdType, error: String?) -> Unit
    ) {
        InputFieldIdType.entries.forEach { field ->
            val error = errorMessages?.get(field.id)?.takeIf { it.isNotEmpty() }?.firstOrNull()

            updateFieldError(field, error)
        }
    }

}