class NetworkConnectionIOS : NetworkConnection {
    override fun isConnected(): Boolean {
        println("Check Network Connection: IOS: ")
        return true
    }
}

actual fun checkNetworkConnection(): NetworkConnection = NetworkConnectionIOS()