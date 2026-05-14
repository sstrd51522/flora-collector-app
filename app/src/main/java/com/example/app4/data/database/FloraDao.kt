package com.example.app4.data.database

import androidx.room.*
import com.example.app4.data.model.PlantSpecimen
import com.example.app4.data.model.SurveySite
import kotlinx.coroutines.flow.Flow

@Dao
interface FloraDao {
    // --- 调查点相关 ---
    @Query("SELECT * FROM survey_sites ORDER BY createTime DESC")
    fun getAllSites(): Flow<List<SurveySite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSite(site: SurveySite)

    @Delete
    suspend fun deleteSite(site: SurveySite)

    // --- 植物标本相关 ---
    @Query("SELECT * FROM plant_specimens WHERE siteId = :siteId")
    fun getSpecimensBySite(siteId: String): Flow<List<PlantSpecimen>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecimen(specimen: PlantSpecimen)

    @Delete
    suspend fun deleteSpecimen(specimen: PlantSpecimen)
}