import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkConnectionAndroid : NetworkConnection {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun initAppContext(context: Context) {
            this. context = context
        }
    }

    override fun isConnected(): Boolean {
        println("Check Network Connection: Android: $context")

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var result = false
        connectivityManager.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                        else -> false
                    }
                }
        }

        println("Check Network Connection: Android: $result")
        return result
    }
}

actual fun checkNetworkConnection(): NetworkConnection = NetworkConnectionAndroid()