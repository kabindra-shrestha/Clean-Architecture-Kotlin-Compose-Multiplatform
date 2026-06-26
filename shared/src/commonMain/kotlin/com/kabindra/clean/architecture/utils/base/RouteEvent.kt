package com.kabindra.clean.architecture.utils.base

import kotlinx.serialization.Serializable

@Serializable
data class RouteEvent(
    var route: String
)