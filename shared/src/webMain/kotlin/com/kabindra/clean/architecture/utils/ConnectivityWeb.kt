package com.kabindra.clean.architecture.utils

import kotlinx.browser.window
import org.w3c.dom.events.Event

actual fun Connectivity(): Connectivity {
    val connectivity = ConnectivityImpl(
        initialConnection = if (window.navigator.onLine) NetworkConnection.WIFI else NetworkConnection.NONE
    )

    window.addEventListener("online", { _: Event ->
        connectivity.onNetworkConnectionChanged(NetworkConnection.WIFI)
    })

    window.addEventListener("offline", { _: Event ->
        connectivity.onNetworkConnectionChanged(NetworkConnection.NONE)
    })

    return connectivity
}
