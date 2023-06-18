package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.purchase

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class PurchaseMastersReportUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(url:String) = apiRepository.getPurchaseMastersReport(url = url)
}