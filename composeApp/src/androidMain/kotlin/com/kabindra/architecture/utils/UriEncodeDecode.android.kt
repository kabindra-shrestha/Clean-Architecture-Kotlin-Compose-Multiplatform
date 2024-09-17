package com.kabindra.architecture.utils

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

actual fun encode(input: String): String {
    return URLEncoder.encode(input, StandardCharsets.UTF_8.toString())
}

actual fun decode(input: String): String {
    return URLDecoder.decode(input, StandardCharsets.UTF_8.toString())
}
