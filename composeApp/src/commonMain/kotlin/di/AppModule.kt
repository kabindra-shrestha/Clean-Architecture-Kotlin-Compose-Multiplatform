package di

import data.repository.remote.NewsRepositoryImpl
import data.source.remote.KtorApiService
import data.source.remote.NewsDataSource
import domain.repository.remote.NewsRepository
import domain.usecase.remote.GetNewsUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.viewmodel.NewsViewModel

val provideHttpClientModule = module {
    single {
        HttpClient {
            install(HttpTimeout) {
                socketTimeoutMillis = 60_000
                requestTimeoutMillis = 60_000
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
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                url("BASE_URL")
                contentType(ContentType.Application.Json)
            }
        }
    }
}

val provideApiServiceModule = module {
    singleOf(::KtorApiService)
}
val provideDataSourceModule = module {
    singleOf(::NewsDataSource)
}

val provideRepositoryModule = module {
    singleOf(::NewsRepositoryImpl).bind<NewsRepository>()
}

val provideUseCaseModule = module {
    singleOf(::GetNewsUseCase)
}

val provideViewModelModule = module {
    viewModelOf(::NewsViewModel)
}

val appModule = listOf(
    provideHttpClientModule,
    provideApiServiceModule,
    provideDataSourceModule,
    provideRepositoryModule,
    provideUseCaseModule,
    provideViewModelModule
)

expect val platformModule: Module