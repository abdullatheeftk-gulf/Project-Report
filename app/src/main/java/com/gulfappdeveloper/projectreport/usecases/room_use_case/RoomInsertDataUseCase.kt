package com.gulfappdeveloper.projectreport.usecases.room_use_case

import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import com.gulfappdeveloper.projectreport.repositories.RoomDatabaseRepository

class RoomInsertDataUseCase(
    private val roomDatabaseRepository: RoomDatabaseRepository
) {
    suspend operator fun invoke(localCompanyData: LocalCompanyData) =
        roomDatabaseRepository.insertData(localCompanyData = localCompanyData)
}