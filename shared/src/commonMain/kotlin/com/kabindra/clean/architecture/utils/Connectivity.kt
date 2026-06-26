package com.kabindra.clean.architecture.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/* Connectivity Implementation
val connectivity: Connectivity = Connectivity()

val isConnected: Boolean = connectivity.isConnected

val networkConnection: NetworkConnection = connectivity.currentNetworkConnection
when (networkConnection) {
    NetworkConnection.NONE -> "Not connected to the internet"
    NetworkConnection.WIFI -> "Connected to wifi"
    NetworkConnection.CELLULAR -> "Connected to cellular"
}

GlobalScope.launch {
    connectivity.isConnectedState.collect { isConnected ->
        // insert code
    }
}

GlobalScope.launch {
    connectivity.currentNetworkConnectionState.collect { connection ->
        when (connection) {
            NetworkConnection.NONE -> "Not connected to the internet"
            NetworkConnection.WIFI -> "Connected to wifi"
            NetworkConnection.CELLULAR -> "Connected to cellular"
        }
    }
}*/

interface Connectivity {
    val isConnected: Boolean
    val currentNetworkConnection: NetworkConnection
    val isConnectedState: StateFlow<Boolean>
    val currentNetworkConnectionState: StateFlow<NetworkConnection>
}

expect fun Connectivity(): Connectivity

internal class ConnectivityImpl(
    initialConnection: NetworkConnection = NetworkConnection.NONE,
    ioDispatcher: CoroutineDispatcher = Dispatchers.Default
) : Connectivity {

    private val scope = CoroutineScope(ioDispatcher)

    private val state = MutableStateFlow<NetworkConnection>(initialConnection)

    override val isConnected: Boolean
        get() = state.value != NetworkConnection.NONE

    override val currentNetworkConnection: NetworkConnection
        get() = state.value

    override val isConnectedState: StateFlow<Boolean> =
        state.asStateFlow()
            .map(scope) { /*it != NetworkConnection.NONE*/ true }

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