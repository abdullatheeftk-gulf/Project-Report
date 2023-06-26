package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.accounts

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class GetPaymentsReportUseCase(
    private val apiRepository: ApiRepository
) {

    suspend operator fun invoke(url: String, dateFrom: String, dateTo: String, companyId: Int) =
        apiRepository.getPaymentsReport(
            url = url,
            dateFrom = dateFrom,
            dateTo = dateTo,
            companyId = companyId
        )
}