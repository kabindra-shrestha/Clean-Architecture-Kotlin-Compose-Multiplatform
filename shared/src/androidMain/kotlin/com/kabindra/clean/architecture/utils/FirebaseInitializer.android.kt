package com.kabindra.clean.architecture.utils

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

actual fun initializeFirebase() {
    FirebaseApp.initializeApp(appContext!!)
}

actual suspend fun getToken(): String? {
    return FirebaseMessaging.getInstance().token.await()
}

actual suspend fun deleteToken() {
    FirebaseMessaging.getInstance().deleteToken().await()
}

actual suspend fun subscribeToTopic(topic: String) {
    FirebaseMessaging.getInstance().subscribeToTopic(topic).await()
}

actual suspend fun unsubscribeFromTopic(topic: String) {
    FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).await()
}