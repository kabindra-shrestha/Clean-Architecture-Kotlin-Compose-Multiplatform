package com.kabindra.clean.architecture.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

expect fun initializeFirebase()

expect suspend fun getToken(): String?

expect suspend fun deleteToken()

expect suspend fun subscribeToTopic(topic: String)

expect suspend fun unsubscribeFromTopic(topic: String)

object NavigationManager {
    private val _navigationState = MutableSharedFlow<NavigationEvent>(replay = 1)
    val navigationState: SharedFlow<NavigationEvent> = _navigationState.asSharedFlow()

    suspend fun handleNavigation(type: String, ticket_id: String, workflow: String, date: String) {
        _navigationState.emit(NavigationEvent(ticket_id, type, workflow, date))
    }

    suspend fun resetNavigationState() {
        _navigationState.emit(NavigationEvent("", "", "", ""))
    }
}

fun handleNavigationRedirection(type: String, ticket_id: String, workflow: String, date: String) {
    CoroutineScope(Dispatchers.Main).launch {
        NavigationManager.handleNavigation(
            type = type,
            ticket_id = ticket_id,
            workflow = workflow,
            date = date
        )
    }
}

data class NavigationEvent(
    val ticket_id: String?,
    val type: String?,
    val workflow: String?,
    val date: String?
)


