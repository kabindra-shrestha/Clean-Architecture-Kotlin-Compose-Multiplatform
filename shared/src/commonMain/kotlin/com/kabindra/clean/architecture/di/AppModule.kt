package com.kabindra.clean.architecture.di

import androidx.compose.material3.SnackbarHostState
import com.kabindra.clean.architecture.data.repository.remote.LoginRepositoryImpl
import com.kabindra.clean.architecture.data.repository.remote.LogoutRepositoryImpl
import com.kabindra.clean.architecture.data.repository.remote.RefreshTokenRepositoryImpl
import com.kabindra.clean.architecture.data.repository.room.AuthenticationRoomRepositoryImpl
import com.kabindra.clean.architecture.data.repository.room.UserRoomRepositoryImpl
import com.kabindra.clean.architecture.data.source.remote.ApiDataSource
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
import com.kabindra.clean.architecture.presentation.ui.screen.dashboard.DashboardViewModel
import com.kabindra.clean.architecture.presentation.ui.screen.splash.SplashViewModel
import com.kabindra.clean.architecture.presentation.viewmodel.room.AuthenticationRoomViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun provideHttpClient(
    appDatabase: AppDatabase,
    tokenProvider: TokenProvider
): HttpClient

val provideAppModule = module {
    single { SnackbarHostState() }
}

val provideHttpClientModule = module {
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
    viewModelOf(::SplashViewModel)
    viewModelOf(::DashboardViewModel)

    viewModelOf(::AuthenticationRoomViewModel)
}

expect val platformModule: Module

fun invalidateAuthTokens(client: HttpClient) {
    val authProvider = client.authProvider<BearerAuthProvider>()

    // requireNotNull(authProvider)

    authProvider?.clearToken()
}