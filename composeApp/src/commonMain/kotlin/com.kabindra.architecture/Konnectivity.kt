package com.kabindra.architecture

import com.kabindra.architecture.NetworkConnection.NONE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/* Konnectivity Implementation
val konnectivity: Konnectivity = Konnectivity()

val isConnected: Boolean = konnectivity.isConnected

val networkConnection: NetworkConnection = konnectivity.currentNetworkConnection
when (networkConnection) {
    NetworkConnection.NONE -> "Not connected to the internet"
    NetworkConnection.WIFI -> "Connected to wifi"
    NetworkConnection.CELLULAR -> "Connected to cellular"
}

GlobalScope.launch {
    konnectivity.isConnectedState.collect { isConnected ->
        // insert code
    }
}

GlobalScope.launch {
    konnectivity.currentNetworkConnectionState.collect { connection ->
        when (connection) {
            NetworkConnection.NONE -> "Not connected to the internet"
            NetworkConnection.WIFI -> "Connected to wifi"
            NetworkConnection.CELLULAR -> "Connected to cellular"
        }
    }
}*/

interface Konnectivity {
    val isConnected: Boolean
    val currentNetworkConnection: NetworkConnection
    val isConnectedState: StateFlow<Boolean>
    val currentNetworkConnectionState: StateFlow<NetworkConnection>
}

expect fun Konnectivity(): Konnectivity

internal class KonnectivityImpl(
    initialConnection: NetworkConnection = NONE,
    ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) : Konnectivity {

    private val scope = CoroutineScope(ioDispatcher)

    private val state = MutableStateFlow<NetworkConnection>(initialConnection)

    override val isConnected: Boolean
        get() = state.value != NONE

    override val currentNetworkConnection: NetworkConnection
        get() = state.value

    override val isConnectedState: StateFlow<Boolean> =
        state.asStateFlow()
            .map(scope) { it != NONE }

    override val currentNetworkConnectionState: StateFlow<NetworkConnection> = state.asStateFlow()

    fun onNetworkConnectionChanged(connection: NetworkConnection) {
        state.value = connection
    }

    private fun <T, M> StateFlow<T>.map(
        coroutineScope: CoroutineScope,
        mapper: (value: T) -> M
    ): StateFlow<M> = map { mapper(it) }.stateIn(
        coroutineScope,
        SharingStarted.Eagerly,
        mapper(value)
    )
}