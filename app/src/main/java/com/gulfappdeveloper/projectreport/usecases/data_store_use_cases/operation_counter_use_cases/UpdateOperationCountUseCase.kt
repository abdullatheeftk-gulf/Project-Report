package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.operation_counter_use_cases

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class UpdateOperationCountUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(){
        dataStoreRepository.updateOperationCount()
    }
}