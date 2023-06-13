package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.sales

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class GetUserSalesReportUseCase(private val apiRepository: ApiRepository) {
    suspend operator  fun invoke(url:String) = apiRepository.getUserSalesReport(url = url)

}