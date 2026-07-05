package com.kabindra.clean.architecture.utils

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

class IOSConfig : Config {
    @OptIn(ExperimentalNativeApi::class)
    override val isDebug: Boolean = Platform.isDebugBinary
}

actual fun getConfig(): Config = IOSConfig()