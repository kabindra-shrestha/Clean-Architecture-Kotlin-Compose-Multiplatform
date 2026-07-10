package com.kabindra.clean.architecture.utils

interface Config {
    val isDebug: Boolean
    val env: String
    val flavor: String
    val baseUrl: String
}

expect fun getConfig(): Config