package di

import data.repository.remote.NewsRepositoryImpl
import data.source.remote.KtorApiService
import data.source.remote.NewsDataSource
import domain.repository.remote.NewsRepository
import domain.usecase.remote.GetNewsUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import presentation.viewmodel.NewsViewModel

val provideHttpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true },
                    contentType = ContentType.Application.Json
                )
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}

val provideApiServiceModule = module {
    single { KtorApiService(get()) }
}
val provideDataSourceModule = module {
    single { NewsDataSource(get()) }
}

val provideRepositoryModule = module {
    single<NewsRepository> { NewsRepositoryImpl(get()) }
}

val provideUseCaseModule = module {
    single { GetNewsUseCase(get()) }
}

val provideViewModelModule = module {
    viewModel { NewsViewModel(get()) }
}

val appModule = listOf(
    provideHttpClientModule,
    provideApiServiceModule,
    provideDataSourceModule,
    provideRepositoryModule,
    provideUseCaseModule,
    provideViewModelModule
)