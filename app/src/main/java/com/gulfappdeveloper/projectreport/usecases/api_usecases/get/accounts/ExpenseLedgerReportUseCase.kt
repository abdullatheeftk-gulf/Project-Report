package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.accounts

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class ExpenseLedgerReportUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(
        url: String,
        dateFrom: String,
        dateTo: String,
        expenseId: Int,
        companyId: Int
    ) = apiRepository.getExpenseLedgerReport(
        url = url,
        dateFrom = dateFrom,
        dateTo = dateTo,
        expenseId = expenseId,
        companyId = companyId
    )
}