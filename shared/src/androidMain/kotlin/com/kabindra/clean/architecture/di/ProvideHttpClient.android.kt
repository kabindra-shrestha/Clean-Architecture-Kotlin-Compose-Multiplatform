package com.kabindra.clean.architecture.di

import com.gyanoba.inspektor.Inspektor
import com.kabindra.clean.architecture.data.model.RefreshTokenDTO
import com.kabindra.clean.architecture.data.source.remote.ApiEndpoints
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE_APP_VERSION
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE_APP_VERSION_CODE
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE_BRAND
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE_BUILD
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE_KEY
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE_MODEL
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE_PLATFORM
import com.kabindra.clean.architecture.utils.constants.Header.Companion.HEADER_USER_DEVICE_VERSION
import com.kabindra.clean.architecture.utils.enums.Status
import com.kabindra.clean.architecture.utils.enums.getStatus
import com.kabindra.clean.architecture.utils.getConfig
import com.kabindra.clean.architecture.utils.getPlatform
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

actual fun provideHttpClient(
    appDatabase: AppDatabase,
    tokenProvider: TokenProvider
): HttpClient {

    return HttpClient(OkHttp) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(
                json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                },
                contentType = ContentType.Application.Json
            )
        }

        install(HttpTimeout) {
            socketTimeoutMillis = 60_000
            requestTimeoutMillis = 60_000
        }

        install(UserAgent) {
            agent = getPlatform().userAgent
        }

        install(DefaultRequest) {
            runBlocking {
                val baseUrl = "baseUrl"
                url(baseUrl)
                contentType(ContentType.Application.Json)

                val platform = getPlatform()

                header(HEADER_USER_DEVICE, platform.userDevice + HEADER_USER_DEVICE_KEY)
                header(HEADER_USER_DEVICE_PLATFORM, platform.devicePlatform)
                header(HEADER_USER_DEVICE_VERSION, platform.deviceVersion)
                header(HEADER_USER_DEVICE_BUILD, platform.deviceBuild)
                header(HEADER_USER_DEVICE_BRAND, platform.deviceBrand)
                header(HEADER_USER_DEVICE_MODEL, platform.deviceModel)
                header(HEADER_USER_DEVICE_APP_VERSION, platform.appVersion)
                header(HEADER_USER_DEVICE_APP_VERSION_CODE, platform.appVersionCode)
            }
        }

        install(Auth) {
            bearer {
                loadTokens {
                    runBlocking {
                        val access = tokenProvider.getAccessToken()
                        val refresh = tokenProvider.getRefreshToken()
                        if (access.isNotEmpty() && refresh.isNotEmpty())
                            BearerTokens(access, refresh)
                        else null
                    }
                }

                refreshTokens {
                    runBlocking {
                        val refresh = tokenProvider.getRefreshToken()
                        val access = tokenProvider.getAccessToken()

                        if (refresh.isEmpty() || access.isEmpty()) return@runBlocking null

                        val response = client.submitForm(
                            url = ApiEndpoints.API_REFRESH_TOKEN,
                            formParameters = parameters {
                                append("refresh_token", refresh)
                            }
                        ) {
                            markAsRefreshTokenRequest()

                            header("Authorization", "Bearer $access")

                            val platform = getPlatform()
                            header(HEADER_USER_DEVICE, platform.userDevice + HEADER_USER_DEVICE_KEY)
                            header(HEADER_USER_DEVICE_PLATFORM, platform.devicePlatform)
                            header(HEADER_USER_DEVICE_VERSION, platform.deviceVersion)
                            header(HEADER_USER_DEVICE_BUILD, platform.deviceBuild)
                            header(HEADER_USER_DEVICE_BRAND, platform.deviceBrand)
                            header(HEADER_USER_DEVICE_MODEL, platform.deviceModel)
                            header(HEADER_USER_DEVICE_APP_VERSION, platform.appVersion)
                            header(HEADER_USER_DEVICE_APP_VERSION_CODE, platform.appVersionCode)
                        }

                        if (!response.status.isSuccess()) {
                            tokenProvider.clearTokens()
                            invalidateAuthTokens(client)
                            return@runBlocking null
                        }

                        val body: RefreshTokenDTO = response.body()

                        if (!getStatus<Status>(body.status)) {
                            tokenProvider.clearTokens()
                            invalidateAuthTokens(client)
                            return@runBlocking null
                        }

                        val tokens = body.response
                        if (tokens?.token.isNullOrEmpty() || tokens.refresh_token.isNullOrEmpty()) {
                            tokenProvider.clearTokens()
                            invalidateAuthTokens(client)
                            return@runBlocking null
                        }

                        tokenProvider.updateTokens(tokens.token!!, tokens.refresh_token!!)
                        BearerTokens(tokens.token, tokens.refresh_token)
                    }
                }

                sendWithoutRequest { request ->
                    when (request.url.encodedPath) {
                        ApiEndpoints.API_REFRESH_TOKEN -> false
                        "/check-user", "/verify-otp" -> false
                        else -> true
                    }
                }

            }
        }

        if (getConfig().isDebug) {
            install(Inspektor)
        }
    }
}