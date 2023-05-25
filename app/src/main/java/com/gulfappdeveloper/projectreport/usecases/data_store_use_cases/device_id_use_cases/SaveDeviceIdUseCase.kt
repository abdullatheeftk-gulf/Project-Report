package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.device_id_use_cases

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class SaveDeviceIdUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(deviceId:String){
        dataStoreRepository.saveDeviceId(deviceId = deviceId)
    }
}