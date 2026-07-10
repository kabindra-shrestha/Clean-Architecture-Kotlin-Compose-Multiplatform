package com.kabindra.clean.architecture.utils

import com.kabindra.clean.architecture.shared.BuildKonfig

class AndroidConfig : Config {
    override val isDebug: Boolean = BuildKonfig.IS_DEBUG
    override val env: String = BuildKonfig.ENV
    override val flavor: String = BuildKonfig.FLAVOR
    override val baseUrl: String = BuildKonfig.BASE_URL
}

actual fun getConfig(): Config = AndroidConfig()
