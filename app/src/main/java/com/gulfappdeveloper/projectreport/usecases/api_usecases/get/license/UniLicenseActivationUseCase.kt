package com.gulfappdeveloper.projectreport.usecases.api_usecases.get.license

import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseRequestBody
import com.gulfappdeveloper.projectreport.domain.models.license.LicenseResponse
import com.gulfappdeveloper.projectreport.repositories.ApiRepository
import kotlinx.coroutines.flow.Flow

class UniLicenseActivationUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(
        rioLabKey: String = "riolab123456",
        url: String,
        licenseRequestBody: LicenseRequestBody
    ): Flow<GetDataFromRemote<LicenseResponse>> {
        return apiRepository.uniLicenseActivation(
            rioLabKey = rioLabKey,
            url = url,
            licenseRequestBody = licenseRequestBody
        )
    }
}