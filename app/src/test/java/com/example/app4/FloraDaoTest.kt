package com.example.app4

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.app4.data.database.AppDatabase
import com.example.app4.data.database.FloraDao
import com.example.app4.data.model.PlantSpecimen
import com.example.app4.data.model.SurveySite
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE) // 避免找不到清单的警告
class FloraDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: FloraDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // 使用内存数据库，测试完即刻销毁，不占用手机空间
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.floraDao()
    }

    @After
    fun closeDb() = db.close()

    // 测试一：建站并录入标本 (对齐“一对多”需求)
    @Test
    fun testInsertSiteAndSpecimen() = runBlocking {
        val site = SurveySite(longitude = 113.8, latitude = 22.9, altitude = 50.0, weather = "晴", habitatType = "林下")
        dao.insertSite(site)
        println("\n[1. 建站成功] -> ID: ${site.siteId}")

        val specimen = PlantSpecimen(siteId = site.siteId, plantName = "荔枝", lifeForm = "乔木", notes = "莞工后山", imagePath = "/sdcard/1.jpg")
        dao.insertSpecimen(specimen)
        println("[2. 标本录入] -> 名称: ${specimen.plantName}, 关联站点: ${specimen.siteId}")

        val specimens = dao.getSpecimensBySite(site.siteId).first()
        assertEquals(1, specimens.size)
        assertEquals("荔枝", specimens[0].plantName)
        println("✅ 测试通过：调查点与标本关联准确！")
    }

    // 测试二：删除标本逻辑
    @Test
    fun testDeleteSpecimen() = runBlocking {
        val site = SurveySite(longitude = 113.8, latitude = 22.9, altitude = 5.0, weather = "阴", habitatType = "灌丛")
        dao.insertSite(site)

        val specimen = PlantSpecimen(siteId = site.siteId, plantName = "待删植物", lifeForm = "草本", notes = "测试删除", imagePath = "none")
        dao.insertSpecimen(specimen)

        println("\n[动作] 执行删除标本: ${specimen.plantName}")
        dao.deleteSpecimen(specimen)

        val result = dao.getSpecimensBySite(site.siteId).first()
        assertEquals(0, result.size)
        println("✅ 测试通过：标本已从站点 ${site.siteId} 中彻底抹除！")
    }
}