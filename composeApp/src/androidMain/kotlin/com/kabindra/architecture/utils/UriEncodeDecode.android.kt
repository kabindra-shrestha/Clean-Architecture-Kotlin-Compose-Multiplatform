package com.kabindra.architecture.utils

import android.net.Uri

actual fun encode(input: String): String {
    return Uri.encode(input)
}

actual fun decode(input: String): String {
    return Uri.decode(input)
}
