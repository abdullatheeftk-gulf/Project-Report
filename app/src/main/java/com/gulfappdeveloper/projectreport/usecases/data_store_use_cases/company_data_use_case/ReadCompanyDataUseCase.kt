package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.company_data_use_case

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class ReadCompanyDataUseCase(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke() = dataStoreRepository.readCompanyData()
}