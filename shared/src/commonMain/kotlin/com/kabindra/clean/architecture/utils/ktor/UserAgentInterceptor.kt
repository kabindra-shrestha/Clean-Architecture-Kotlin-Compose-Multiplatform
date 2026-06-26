package com.kabindra.clean.architecture.utils.ktor

class UserAgentInterceptor(private val headersProvider: () -> Map<String, String>)
