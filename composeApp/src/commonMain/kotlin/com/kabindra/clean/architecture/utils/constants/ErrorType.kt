package com.kabindra.clean.architecture.utils.constants

class ErrorType {

    companion object {
        const val ERROR_TITLE_NO_NETWORK_CONNECTIVITY = "No Network Connectivity"
        const val ERROR_TITLE_NO_INTERNET_CONNECTIVITY = "No Internet Connectivity"
        const val ERROR_TITLE_SERVER_MAINTENANCE = "Server Maintenance"
        const val ERROR_TITLE_VERSION_CHECK = "New Update Available"

        const val ERROR_COMMON_EXCEPTION =
            "Code-1000: Something went wrong.\nPlease check your network connection and try again."
        const val ERROR_INTERNAL_SERVER_EXCEPTION =
            "Code-5000: Something went wrong.\nPlease check your network connection and try again."
        const val ERROR_NO_NETWORK_CONNECTIVITY =
            "Code-5220: No Network Connectivity.\nPlease check your network connection and try again."
        const val ERROR_CONNECTION_SHUTDOWN_EXCEPTION =
            "Code-5221: No Internet Connection.\nPlease check your network connection and try again."
        const val ERROR_HTTP_EXCEPTION =
            "Code-5222: No Internet Connection.\nPlease check your network connection and try again."
        const val ERROR_CONNECT_EXCEPTION =
            "Code-5223: The network connection is lost.\nPlease check your network connection and try again."
        const val ERROR_UNKNOWN_HOST_EXCEPTION =
            "Code-5230: The network connection is unavailable.\nPlease check your network connection."
        const val ERROR_NO_ROUTE_TO_HOST_EXCEPTION =
            "Code-5231: The network is unavailable.\nPlease check your network connection and try again."
        const val ERROR_SOCKET_EXCEPTION =
            "Code-5240: It's taking time to load.\nPlease check your network connection and try again."
        const val ERROR_SOCKET_TIMEOUT_EXCEPTION =
            "Code-5241: It's taking time to load content.\nPlease check your network connection and try again."
        const val ERROR_SSL_HANDSHAKE_EXCEPTION =
            "Code-5251: The connection is no longer usable.\nCheck your network connection. Also, check if your Date and Time is correct and try again."
        const val ERROR_SSL_EXCEPTION =
            "Code-5252: The connection is aborted.\nCheck your network connection and try again."
        const val ERROR_JSON_SYNTAX_EXCEPTION =
            "Code-4001: Contents couldn't load due to unexpected response.\nPlease try refreshing it again."
        const val ERROR_MALFORMED_JSON_EXCEPTION =
            "Code-4002: Contents couldn't load due to unacceptable response.\nPlease try refreshing it again."
        const val ERROR_NUMBER_FORMAT_EXCEPTION =
            "Code-4003: Number format mismatched.\nPlease try refreshing it again."


        const val ERROR_EXO_PLAYER_COMMON_EXCEPTION =
            "Code-5001: Please refresh and try again.\nIf the problem persists contact support."
        const val ERROR_EXO_PLAYER_CONNECT_EXCEPTION =
            "Code-5223: The network connection is lost.\nPlease check your network connection and try again."
        const val ERROR_EXO_PLAYER_UNKNOWN_HOST_EXCEPTION =
            "Code-5232: The network connection is unavailable.\nPlease check your network connection and try again."
        const val ERROR_EXO_PLAYER_SOCKET_TIMEOUT_EXCEPTION =
            "Code-5242: It's taking time to load stream.\nPlease check your network connection and try again."
        const val ERROR_EXO_PLAYER_FILE_NOT_FOUND_EXCEPTION =
            "Code-2040: We couldn't find the stream you've requested.\nPlease contact support or try again later."
        const val ERROR_EXO_PLAYER_TOO_MANY_SESSION_EXCEPTION =
            "Code-4030: Too many users detected.\nPlease contact support or try again later."
        const val ERROR_EXO_PLAYER_TOO_MANY_SESSION_PPV_EXCEPTION =
            "Code-4030: Too many users detected.\nPlease contact support or try again later."


        const val ERROR_SQLITE_EXCEPTION =
            "Code-1300: We couldn't perform task due to some unusual issue.\nPlease check your device storage and make some space and try again."

        const val ERROR_SERVER_MAINTENANCE =
            "We are currently working on regular server maintenance.\nPlease try again later or contact our customer service. Thank you."
        const val ERROR_VERSION_CHECK = "Explore New Experiences"
    }

}