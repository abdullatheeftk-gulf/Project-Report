package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.device_id_use_cases

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadDeviceIdUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<String> {
        return dataStoreRepository.readDeviceId()
    }
}