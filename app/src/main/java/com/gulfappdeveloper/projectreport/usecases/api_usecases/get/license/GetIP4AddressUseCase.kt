package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.license

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class GetIP4AddressUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(url: String) =
        apiRepository.getIp4Address(url = url)
}