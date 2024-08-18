package com.kabindra.architecture

interface NetworkConnectionUtils {
    fun isConnected(): Boolean
}

expect fun checkNetworkConnection(): NetworkConnectionUtils