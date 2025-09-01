package com.kabindra.clean.architecture.utils

expect fun getFileUri(filePath: String): Any

expect fun openFile(url: String, extension: String, onError: (String) -> Unit)