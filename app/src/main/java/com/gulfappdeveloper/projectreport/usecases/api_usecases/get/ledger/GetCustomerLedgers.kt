package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.ledger

import com.gulfappdeveloper.projectreport.repositories.ApiRepository

class GetCustomerLedgers
    (private val apiRepository: ApiRepository){
        suspend operator fun invoke(url:String) = apiRepository.getCustomerLedgers(url = url)
}