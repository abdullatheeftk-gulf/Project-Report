package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.activation_status_use_case

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class SaveActivationStatusUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(activationStatus:Boolean) = dataStoreRepository.saveActivationStatus(
        activationStatus = activationStatus
    )
}