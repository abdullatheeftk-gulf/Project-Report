package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.register_and_login_use_cases

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class RegisterCompanyUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator  fun invoke(url:String) = apiRepository.registerCompany(url = url)
}