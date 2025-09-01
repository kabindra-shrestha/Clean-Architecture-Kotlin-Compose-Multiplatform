package com.kabindra.clean.architecture.utils.base

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KClass

// 1. Event Bus Implementation
object EventBus {
    private val mutex = Mutex()

    // Non-sticky events
    private val _nonStickyEvents = MutableSharedFlow<Any>(extraBufferCapacity = 64)
    val nonStickyEvents: SharedFlow<Any> = _nonStickyEvents

    // Sticky events storage
    private val stickyEvents = mutableMapOf<KClass<*>, Any>()

    // Sticky updates stream
    private val _stickyUpdates = MutableSharedFlow<Any>(extraBufferCapacity = 64)
    private val stickyUpdates: SharedFlow<Any> = _stickyUpdates

    fun post(event: Any) {
        _nonStickyEvents.tryEmit(event)
    }

    suspend fun postSticky(event: Any) {
        mutex.withLock {
            stickyEvents[event::class] = event
        }
        _stickyUpdates.emit(event)
        post(event)
    }

    suspend fun <T : Any> getStickyEvent(eventType: KClass<T>): T? {
        return mutex.withLock {
            @Suppress("UNCHECKED_CAST")
            stickyEvents[eventType] as? T
        }
    }

    fun <T : Any> observeSticky(eventType: KClass<T>): Flow<T?> {
        return stickyUpdates
            .filter { it::class == eventType }
            .map { it as T }
            .onStart {
                val current = getStickyEvent(eventType)
                if (current != null) emit(current)
            }
    }

    suspend fun <T : Any> clearStickyEvent(eventType: KClass<T>) {
        mutex.withLock { stickyEvents.remove(eventType) }
    }

    suspend fun clearAllStickyEvents() {
        mutex.withLock { stickyEvents.clear() }
    }
}

// 2. Example Event Classes
data class UserUpdateEvent(val userId: String, val newName: String)
data class AppNotificationEvent(val message: String)

// 3. Example Composables
@Composable
fun EventBusDemoApp() {
    var currentScreen by remember { mutableStateOf("Home") }

    when (currentScreen) {
        "Home" -> HomeScreen { currentScreen = "Profile" }
        "Profile" -> ProfileScreen { currentScreen = "Home" }
    }
}

@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit) {
    val scope = rememberCoroutineScope()
    var lastNotification by remember { mutableStateOf<String?>(null) }

    // Observe non-sticky notifications
    LaunchedEffect(Unit) {
        EventBus.nonStickyEvents.collect { event ->
            if (event is AppNotificationEvent) {
                lastNotification = event.message
            }
        }
    }

    // Observe sticky user updates
    var userUpdate by remember { mutableStateOf<UserUpdateEvent?>(null) }
    LaunchedEffect(Unit) {
        EventBus.observeSticky(UserUpdateEvent::class).collect { event ->
            userUpdate = event
        }
    }

    Column {
        Text("Last Notification: ${lastNotification ?: "None"}")
        Text("Sticky User Update: ${userUpdate?.newName ?: "None"}")

        Button(onClick = {
            scope.launch {
                EventBus.post(
                    AppNotificationEvent("Home button clicked!")
                )
            }
        }) {
            Text("Send Notification")
        }

        Button(onClick = onNavigateToProfile) {
            Text("Go to Profile")
        }
    }
}

@Composable
fun ProfileScreen(onNavigateBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    var userName by remember { mutableStateOf("Anonymous") }

    // Get initial sticky value
    LaunchedEffect(Unit) {
        val initialUpdate = EventBus.getStickyEvent(UserUpdateEvent::class)
        initialUpdate?.let { userName = it.newName }
    }

    Column {
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("User Name") }
        )

        Button(onClick = {
            scope.launch {
                EventBus.postSticky(
                    UserUpdateEvent("user_123", userName)
                )
                EventBus.post(
                    AppNotificationEvent("Profile updated!")
                )
            }
        }) {
            Text("Save Changes")
        }

        Button(onClick = onNavigateBack) {
            Text("Back to Home")
        }
    }
}