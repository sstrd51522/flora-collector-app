package com.example.app4.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "plant_specimens",
    foreignKeys = [
        ForeignKey(
            entity = SurveySite::class,
            parentColumns = ["siteId"],
            childColumns = ["siteId"],
            onDelete = ForeignKey.CASCADE // 主表调查点删除，关联标本自动删除
        )
    ],
    indices = [Index(value = ["siteId"])] // 为外键建立索引提高查询效率
)
data class PlantSpecimen(
    @PrimaryKey
    val specimenId: String = UUID.randomUUID().toString(), //
    val siteId: String,       // 关联调查点 ID
    val plantName: String,    // 中文名称
    val lifeForm: String,     // 生活型（乔木/灌木等）
    val phenology: String = "营养期", // 物候期
    val notes: String,        // 观察笔记
    val imagePath: String,    // 图片本地路径
    val syncStatus: Int = 0   // 同步状态
)