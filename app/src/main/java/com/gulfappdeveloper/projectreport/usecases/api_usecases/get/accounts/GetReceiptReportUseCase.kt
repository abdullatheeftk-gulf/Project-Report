package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.accounts

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class GetReceiptReportUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(url: String, dateFrom: String, dateTo: String, companyId: Int) =
        apiRepository.getReceiptReport(
            url = url,
            dateFrom = dateFrom,
            dateTo = dateTo,
            companyId = companyId
        )
}