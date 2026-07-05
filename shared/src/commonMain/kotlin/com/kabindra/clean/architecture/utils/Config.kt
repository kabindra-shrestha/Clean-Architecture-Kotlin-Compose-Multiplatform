package com.kabindra.clean.architecture.utils

interface Config {
    val isDebug: Boolean
}

expect fun getConfig(): Config