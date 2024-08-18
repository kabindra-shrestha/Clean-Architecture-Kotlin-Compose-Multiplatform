package com.kabindra.architecture

class NetworkConnectionIOS : NetworkConnectionUtils {
    override fun isConnected(): Boolean {
        println("Check Network Connection: IOS: ")
        return true
    }
}

actual fun checkNetworkConnection(): NetworkConnectionUtils = NetworkConnectionIOS()