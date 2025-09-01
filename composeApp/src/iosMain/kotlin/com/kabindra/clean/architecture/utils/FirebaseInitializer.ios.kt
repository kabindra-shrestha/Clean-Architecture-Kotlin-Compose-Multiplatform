package com.kabindra.clean.architecture.utils

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.messaging.messaging

actual fun initializeFirebase() {
    // Firebase initialization logic is now handled in AppDelegate.
    // Firebase.initialize()
}

actual suspend fun getToken(): String? {
    return Firebase.messaging.getToken()
}

actual suspend fun deleteToken() {
    Firebase.messaging.deleteToken()
}

actual suspend fun subscribeToTopic(topic: String) {
    Firebase.messaging.subscribeToTopic(topic)
}

actual suspend fun unsubscribeFromTopic(topic: String) {
    Firebase.messaging.unsubscribeFromTopic(topic)
}