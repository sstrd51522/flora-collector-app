package com.example.app4.data.repository

import com.example.app4.data.database.FloraDao
import com.example.app4.data.model.SurveySite
import com.example.app4.data.model.PlantSpecimen
import kotlinx.coroutines.flow.Flow

class FloraRepository(private val dao: FloraDao) {

    // 暴露给 UI 的观察流
    val allSites: Flow<List<SurveySite>> = dao.getAllSites()

    // 离线建站逻辑 [cite: 4, 7]
    suspend fun createSite(site: SurveySite) {
        dao.insertSite(site)
    }

    // 录入植物逻辑 [cite: 4, 7]
    suspend fun addSpecimen(specimen: PlantSpecimen) {
        dao.insertSpecimen(specimen)
    }

    suspend fun deleteSite(site: SurveySite) {
        dao.deleteSite(site)
    }

}