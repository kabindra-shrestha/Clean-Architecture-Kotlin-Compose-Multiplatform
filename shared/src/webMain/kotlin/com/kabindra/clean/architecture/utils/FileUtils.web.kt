package com.kabindra.clean.architecture.utils

import kotlinx.browser.window

actual fun getFileUri(filePath: String): Any {
    return filePath
}

actual fun openFile(
    url: String,
    extension: String,
    onError: (String) -> Unit
) {
    val newWindow = window.open(url, "_blank")
    if (newWindow == null) {
        onError("Please allow popups for this website")
    }
}
