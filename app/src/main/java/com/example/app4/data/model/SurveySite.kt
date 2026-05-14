package com.example.app4.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "survey_sites")
data class SurveySite(
    @PrimaryKey
    val siteId: String = UUID.randomUUID().toString(), // 文档要求：UUID 主键 [cite: 1, 7]
    val longitude: Double,   // 经度
    val latitude: Double,    // 纬度
    val altitude: Double,    // 海拔
    val weather: String,     // 天气
    val habitatType: String, // 生境类型
    val createTime: Long = System.currentTimeMillis(), // 建站时间戳
    val syncStatus: Int = 0  // 同步状态：0待同步，1已同步
)