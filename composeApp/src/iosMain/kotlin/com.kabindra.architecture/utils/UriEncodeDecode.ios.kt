package com.kabindra.architecture.utils

import platform.Foundation.NSCharacterSet
import platform.Foundation.NSString
import platform.Foundation.URLQueryAllowedCharacterSet
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.stringByRemovingPercentEncoding

actual fun encode(input: String): String {
    return (input as NSString).stringByAddingPercentEncodingWithAllowedCharacters(
        NSCharacterSet.URLQueryAllowedCharacterSet()
    ) ?: input
}

actual fun decode(input: String): String {
    return (input as NSString).stringByRemovingPercentEncoding() ?: input
}

