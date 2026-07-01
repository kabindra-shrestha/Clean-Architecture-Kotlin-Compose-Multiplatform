package com.kabindra.clean.architecture.di

import androidx.compose.material3.SnackbarHostState
import com.kabindra.clean.architecture.data.model.RefreshTokenDTO
import com.kabindra.clean.architecture.data.repository.remote.LoginRepositoryImpl
import com.kabindra.clean.architecture.data.repository.remote.LogoutRepositoryImpl
import com.kabindra.clean.architecture.data.repository.remote.RefreshTokenRepositoryImpl
import com.kabindra.clean.architecture.data.repository.room.AuthenticationRoomRepositoryImpl
import com.kabindra.clean.architecture.data.repository.room.UserRoomRepositoryImpl
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
import com.kabindra.clean.architecture.data.source.remote.ApiEndpoints
import com.kabindra.clean.architecture.data.source.remote.ApiService
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.domain.repository.remote.LoginRepository
import com.kabindra.clean.architecture.domain.repository.remote.LogoutRepository
import com.kabindra.clean.architecture.domain.repository.remote.RefreshTokenRepository
import com.kabindra.clean.architecture.domain.repository.room.AuthenticationRoomRepository
import com.kabindra.clean.architecture.domain.repository.room.UserRoomRepository
import com.kabindra.clean.architecture.domain.usecase.remote.LoginUseCase
import com.kabindra.clean.architecture.domain.usecase.remote.LogoutUseCase
import com.kabindra.clean.architecture.domain.usecase.remote.RefreshTokenUseCase
import com.kabindra.clean.architecture.domain.usecase.room.AuthenticationRoomUseCase
import com.kabindra.clean.architecture.domain.usecase.room.UserRoomUseCase
import com.kabindra.clean.architecture.presentation.viewmodel.remote.LoginViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.remote.LogoutViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.remote.RefreshTokenViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.remote.SplashViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.room.AuthenticationRoomViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.room.UserRoomViewModel
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
import com.kabindra.clean.architecture.utils.getPlatform
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val provideAppModule = module {
    single { SnackbarHostState() }
}

val provideHttpClientModule = module {
    fun provideHttpClient(
        appDatabase: AppDatabase,
        tokenProvider: TokenProvider
    ): HttpClient {
        return HttpClient {
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
                url("baseUrl")

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
            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = tokenProvider.getAccessToken()
                        val refreshToken = tokenProvider.getRefreshToken()
                        if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                            BearerTokens(accessToken, refreshToken)
                        } else {
                            tokenProvider.clearTokens()

                            null
                            // BearerTokens("", "")
                        }
                    }
                    refreshTokens {
                        val refreshToken = tokenProvider.getRefreshToken()
                        if (refreshToken.isNotEmpty()) {
                            val response: HttpResponse = client.submitForm(
                                url = ApiEndpoints.API_REFRESH_TOKEN,
                                formParameters = parameters {
                                    append("refresh_token", refreshToken)
                                }
                            ) {
                                markAsRefreshTokenRequest()
                            }
                            if (response.status.isSuccess()) {
                                val responses: RefreshTokenDTO = response.body()

                                if (getStatus<Status>(responses.status)) {
                                    tokenProvider.updateTokens(
                                        responses.response?.token!!,
                                        responses.response.refresh_token!!
                                    )

                                    BearerTokens(
                                        responses.response.token,
                                        responses.response.refresh_token
                                    )
                                } else {
                                    tokenProvider.clearTokens()

                                    invalidateAuthTokens(client)

                                    null
                                    // BearerTokens("", "")
                                }
                            } else {
                                tokenProvider.clearTokens()

                                invalidateAuthTokens(client)

                                null
                                // BearerTokens("", "")
                            }
                        } else {
                            tokenProvider.clearTokens()

                            invalidateAuthTokens(client)

                            null
                            // BearerTokens("", "")
                        }
                    }
                    sendWithoutRequest { request ->
                        when (request.url.pathSegments.last()) {
                            "check-user" -> false
                            "verify-otp" -> false
                            else -> true
                        }
                    }
                }
            }
        }
    }

    singleOf(::TokenProvider)
    singleOf(::provideHttpClient)
}

val provideApiServiceModule = module {
    singleOf(::ApiService)
}

val provideDataSourceModule = module {
    singleOf(::ApiDataSource)
}

val provideRepositoryModule = module {
    singleOf(::LoginRepositoryImpl).bind<LoginRepository>()
    singleOf(::LogoutRepositoryImpl).bind<LogoutRepository>()
    singleOf(::RefreshTokenRepositoryImpl).bind<RefreshTokenRepository>()

    singleOf(::AuthenticationRoomRepositoryImpl).bind<AuthenticationRoomRepository>()
    singleOf(::UserRoomRepositoryImpl).bind<UserRoomRepository>()
}

val provideUseCaseModule = module {
    singleOf(::LoginUseCase)
    singleOf(::LogoutUseCase)
    singleOf(::RefreshTokenUseCase)

    singleOf(::AuthenticationRoomUseCase)
    singleOf(::UserRoomUseCase)
}

val provideViewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::LogoutViewModel)
    viewModelOf(::RefreshTokenViewModel)
    viewModelOf(::SplashViewModel)

    viewModelOf(::AuthenticationRoomViewModel)
    viewModelOf(::UserRoomViewModel)
}

expect val platformModule: Module

fun invalidateAuthTokens(client: HttpClient) {
    val authProvider = client.authProvider<BearerAuthProvider>()

    // requireNotNull(authProvider)

    authProvider?.clearToken()
}