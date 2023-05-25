package com.gulfappdeveloper.projectreport.usecases.data_store_use_cases.uni_license_use_cases

import com.gulfappdeveloper.projectreport.repositories.DataStoreRepository

class SaveUniLicenseUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(uniLicenseString: String) {
        dataStoreRepository.saveUniLicenseKey(uniLicenseString = uniLicenseString)
    }
}