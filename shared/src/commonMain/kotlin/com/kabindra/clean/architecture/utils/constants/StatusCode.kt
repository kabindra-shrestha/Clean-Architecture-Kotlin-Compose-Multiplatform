package com.kabindra.clean.architecture.utils.constants

class StatusCode {

    companion object {
        const val STATUS_CODE_HANDLED = -1
        const val STATUS_CODE_NOT_HANDLED = 0

        const val STATUS_OK = 200
        const val STATUS_SERVER_ERROR = 500
        const val STATUS_TOKEN_INVALID_EXPIRED = 401
        const val STATUS_GEO_ACCESS_DENIED = 503
        const val STATUS_VALIDATION = 411
        const val STATUS_TOO_MANY_REQUESTS = 429
        const val STATUS_PACKAGE_EXPIRED = 426
        const val STATUS_PAYMENT_REQUIRED = 415
        const val STATUS_INTERNET_EXPIRED = 206
        const val STATUS_VERSION_MISMATCHED = 505
        const val STATUS_NOT_FOUND = 404
        const val STATUS_FORBIDDEN = 403

        const val STATUS_COMMON_EXCEPTION = 1000
        const val STATUS_INTERNAL_SERVER_EXCEPTION = 5000
        const val STATUS_NO_NETWORK_CONNECTIVITY = 5220
        const val STATUS_CONNECTION_SHUTDOWN_EXCEPTION = 5221
        const val STATUS_HTTP_EXCEPTION = 5222
        const val STATUS_CONNECT_EXCEPTION = 5223
        const val STATUS_UNKNOWN_HOST_EXCEPTION = 5230
        const val STATUS_NO_ROUTE_TO_HOST_EXCEPTION = 5231
        const val STATUS_SOCKET_EXCEPTION = 5240
        const val STATUS_SOCKET_TIMEOUT_EXCEPTION = 5241
        const val STATUS_SSL_HANDSHAKE_EXCEPTION = 5251
        const val STATUS_SSL_EXCEPTION = 5252
        const val STATUS_JSON_SYNTAX_EXCEPTION = 4001
        const val STATUS_MALFORMED_JSON_EXCEPTION = 4002
        const val STATUS_NUMBER_FORMAT_EXCEPTION = 4003

        const val STATUS_SQLITE_EXCEPTION = 1300

        const val STATUS_PLAYER_STREAM_FORBIDDEN = 403
        const val STATUS_STREAM_FORBIDDEN = 409
    }

}