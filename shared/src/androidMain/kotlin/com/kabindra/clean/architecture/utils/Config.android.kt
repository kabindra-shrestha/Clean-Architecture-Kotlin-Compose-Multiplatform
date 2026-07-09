package com.kabindra.clean.architecture.utils

import android.content.pm.ApplicationInfo

class AndroidConfig : Config {
    override val isDebug: Boolean
        get() = (appContext?.applicationInfo?.flags?.let { (it and ApplicationInfo.FLAG_DEBUGGABLE) != 0 }
            ?: false)
}

actual fun getConfig(): Config = AndroidConfig()