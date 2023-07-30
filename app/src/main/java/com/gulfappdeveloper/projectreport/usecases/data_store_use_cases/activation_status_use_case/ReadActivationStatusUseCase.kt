package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.activation_status_use_case

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class ReadActivationStatusUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.readActivationStatus()
}