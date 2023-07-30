package com.gulfappdeveloper.projectreport.di

import com.gulfappdeveloper.projectreport.data.api.ApiServiceImpl
import com.gulfappdeveloper.projectreport.domain.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android){
            install(ContentNegotiation){
                json(
                    contentType = ContentType.Application.Json,
                    json = Json {
                        encodeDefaults = true
                        ignoreUnknownKeys = false
                    }
                )
            }

            engine {
                connectTimeout = 60_000
                socketTimeout = 60_000
            }

        }
    }

    @Provides
    @Singleton
    fun provideApiService(client: HttpClient): ApiService {
        return ApiServiceImpl(client)
    }
}