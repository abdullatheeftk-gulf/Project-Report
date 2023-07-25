package com.gulfappdeveloper.projectreport.domain.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class LocalCompanyData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val place: String,
    val taxId: String
)