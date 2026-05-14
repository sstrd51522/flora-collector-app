package com.example.app4.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app4.data.model.PlantSpecimen
import com.example.app4.data.model.SurveySite

@Database(
    entities = [SurveySite::class, PlantSpecimen::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun floraDao(): FloraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "flora_collector_db"
                )
                    .fallbackToDestructiveMigration() // 如果之前有旧表，直接升级清空
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}