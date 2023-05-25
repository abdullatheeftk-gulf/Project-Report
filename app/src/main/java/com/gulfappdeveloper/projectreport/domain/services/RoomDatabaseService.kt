package com.gulfappdeveloper.projectreport.domain.services

import com.gulfappdeveloper.projectreport.domain.models.general.GetDataFromRemote
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import kotlinx.coroutines.flow.Flow

interface RoomDatabaseService {

    suspend fun insertCompanyData(companyData: LocalCompanyData)

    suspend fun getAllCompanyData():Flow<List<LocalCompanyData>>
}