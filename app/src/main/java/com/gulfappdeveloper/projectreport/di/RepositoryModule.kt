package com.gulfappdeveloper.projectreport.di

import com.gulfappdeveloper.projectreport.domain.services.ApiService
import com.gulfappdeveloper.projectreport.repositories.ApiRepository
import com.gulfappdeveloper.projectreport.usecases.api_usecases.UseCase
import com.gulfappdeveloper.projectreport.usecases.api_usecases.get.welcome.WelcomeMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCase(
       apiRepository: ApiRepository
    ) = UseCase(
        welcomeMessageUseCase = WelcomeMessageUseCase(apiRepository = apiRepository)
    )
}