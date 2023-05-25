package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.operation_counter_use_cases

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadOperationCountUseCase(
    private val dataStoreRepository: DataStoreRepository
){
    operator fun invoke(): Flow<Int> {
        return dataStoreRepository.readOperationCount()
    }
}