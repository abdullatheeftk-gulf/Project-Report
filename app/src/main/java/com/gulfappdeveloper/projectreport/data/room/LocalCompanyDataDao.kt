package com.gulfappdeveloper.projectreport.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gulfappdeveloper.projectreport.domain.models.room.LocalCompanyData
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalCompanyDataDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCompanyData(localCompanyData: LocalCompanyData)

    @Query("SELECT * FROM LocalCompanyData")
    fun getAllCompanyData(): Flow<List<LocalCompanyData>>
}