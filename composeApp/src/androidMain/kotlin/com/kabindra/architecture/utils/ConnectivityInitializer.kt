package com.kabindra.architecture.utils

import android.content.Context
import androidx.startup.Initializer

internal var appContext: Context? = null

internal class ConnectivityInitializer : Initializer<Context> {
    override fun create(context: Context): Context =
        context.applicationContext.also { appContext = it }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}