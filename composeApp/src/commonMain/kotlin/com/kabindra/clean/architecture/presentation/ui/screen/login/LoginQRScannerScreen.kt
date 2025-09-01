package com.kabindra.clean.architecture.presentation.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.kabindra.clean.architecture.domain.entity.Config
import com.kabindra.clean.architecture.presentation.ui.component.QrScannerScreenComponent
import com.kabindra.clean.architecture.utils.base.ErrorEvent
import com.kabindra.clean.architecture.utils.base.EventBus
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun LoginQRScannerScreen(
    onBackNavigate: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val json = Json { ignoreUnknownKeys = true }
    var config: Config?

    QrScannerScreenComponent(
        onCompletion = { qrCode ->
            try {
                config = json.decodeFromString<Config>(qrCode)

                if (config != null) {
                    if (!config.base_url.isNullOrEmpty()) {
                        scope.launch {
                            EventBus.postSticky(config)
                            onBackNavigate()
                        }
                    } else {
                        scope.launch {
                            showErrorEvent(
                                isVisible = true,
                                isAction = true,
                                statusCode = -1,
                                title = "",
                                message = "Required fields are missing in the scanned QR code."
                            )
                            onBackNavigate()
                        }
                    }
                } else {
                    scope.launch {
                        showErrorEvent(
                            isVisible = true,
                            isAction = true,
                            statusCode = -1,
                            title = "",
                            message = "Required fields are missing in the scanned QR code."
                        )
                        onBackNavigate()
                    }
                }
            } catch (e: SerializationException) {
                scope.launch {
                    showErrorEvent(
                        isVisible = true,
                        isAction = true,
                        statusCode = -1,
                        title = "Invalid QR Code",
                        message = "The scanned QR code format is invalid."
                    )
                    onBackNavigate()
                }
            } catch (e: MissingFieldException) {
                scope.launch {
                    showErrorEvent(
                        isVisible = true,
                        isAction = true,
                        statusCode = -1,
                        title = "Invalid QR Code",
                        message = "Missing required fields"
                    )
                    onBackNavigate()
                }
            } catch (e: Exception) {
                scope.launch {
                    showErrorEvent(
                        isVisible = true,
                        isAction = true,
                        statusCode = -1,
                        title = "Invalid QR Code",
                        message = "An unexpected error occurred"
                    )
                    onBackNavigate()
                }
            }
        },
        onBackNavigate = { onBackNavigate() }
    )
}

suspend fun showErrorEvent(
    isVisible: Boolean = false,
    isAction: Boolean = false,
    statusCode: Int = -1,
    title: String,
    message: String
) {
    EventBus.postSticky(
        ErrorEvent(
            isVisible = isVisible,
            isAction = isAction,
            statusCode = statusCode,
            title = title,
            message = message
        )
    )
}