package com.kabindra.clean.architecture.utils

import kotlin.experimental.ExperimentalNativeApi

class WebConfig : Config {
    @OptIn(ExperimentalNativeApi::class)
    override val isDebug: Boolean = true
}

actual fun getConfig(): Config = WebConfig()