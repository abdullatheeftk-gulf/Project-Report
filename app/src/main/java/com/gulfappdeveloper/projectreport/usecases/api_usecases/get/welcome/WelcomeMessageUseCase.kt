package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.welcome

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class WelcomeMessageUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(url:String) = apiRepository.getWelcomeMessage(url = url)
}