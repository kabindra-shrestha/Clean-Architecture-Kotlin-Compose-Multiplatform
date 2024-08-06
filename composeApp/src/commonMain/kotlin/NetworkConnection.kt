interface NetworkConnection {
    fun isConnected(): Boolean
}

expect fun checkNetworkConnection(): NetworkConnection