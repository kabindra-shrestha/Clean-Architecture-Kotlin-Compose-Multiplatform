package com.kabindra.clean.architecture.utils

actual fun initializeFirebase() {
    // No-op for web targets. Full JS interop could be added here in the future.
}

actual suspend fun getToken(): String? {
    return null
}

actual suspend fun deleteToken() {
    // No-op
}

actual suspend fun subscribeToTopic(topic: String) {
    // No-op
}

actual suspend fun unsubscribeFromTopic(topic: String) {
    // No-op
}
