package com.gulfappdeveloper.projectreport.data.room

import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.domain.services.RoomDatabaseService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDatabaseServiceImpl
    @Inject constructor(
        private val dao:LocalCompanyDataDao
    ):RoomDatabaseService {

    override suspend fun insertCompanyData(companyData: LocalCompanyData) {
        dao.insertCompanyData(localCompanyData = companyData)
    }

    override suspend fun getAllCompanyData(): Flow<List<LocalCompanyData>>{
        return dao.getAllCompanyData()
    }
}