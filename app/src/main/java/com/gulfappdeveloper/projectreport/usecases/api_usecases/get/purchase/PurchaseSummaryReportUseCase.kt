package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.purchase

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class PurchaseSummaryReportUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(
        url: String,
        dateFrom: String,
        dateTo: String,
        companyId: Int
    ) = apiRepository.getPurchaseSummaryReport(
        url = url,
        dateFrom = dateFrom,
        dateTo = dateTo,
        companyId = companyId
    )
}