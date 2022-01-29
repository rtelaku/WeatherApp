package com.example.firstapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [Weather::class, Forecast::class], version = 1, exportSchema = false)
@TypeConverters(
    Converters::class)
abstract class WeatherDatabase : RoomDatabase(){
    abstract fun weatherDao(): WeatherDao

    companion object {
        private lateinit var INSTANCE: WeatherDatabase

        fun getDbInstance(context: Context): WeatherDatabase {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = createDbInstance(context)
            }
            return INSTANCE
        }

        private fun createDbInstance(context: Context) : WeatherDatabase {
            return Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java,
                "WeatherDb")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}