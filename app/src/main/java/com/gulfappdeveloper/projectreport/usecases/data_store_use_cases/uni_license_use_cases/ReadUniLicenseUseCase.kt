package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.uni_license_use_cases

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadUniLicenseUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<String> {
        return dataStoreRepository.readUniLicenseKey()
    }
}