package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.customer_payment

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class GetCustomerPaymentUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(url:String) = apiRepository.getCustomerPaymentReport(url = url)
}