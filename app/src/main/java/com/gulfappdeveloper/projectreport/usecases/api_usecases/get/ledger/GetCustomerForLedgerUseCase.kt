package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.ledger

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class GetCustomerForLedgerUseCase
    (private val apiRepository: ApiRepository) {
        suspend operator fun invoke(url:String) = apiRepository.getCustomerForLedger(url = url)
}