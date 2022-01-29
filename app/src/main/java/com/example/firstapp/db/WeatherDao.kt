package com.example.firstapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE cityLatitude = :lat AND cityLongitude = :lon")
    fun getCityWeather(lat: String?, lon: String?): LiveData<Weather>

    @Query("SELECT * FROM weather WHERE cityLatitude = :lat AND cityLongitude = :lon")
    fun getSelectedCityWeather(lat: String?, lon: String?): Weather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(vararg weather: Weather?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: ArrayList<Forecast>)

    @Query("SELECT * FROM forecast WHERE  cityLatitude = :lat AND cityLongitude = :lon")
    fun getDbForecast(lat: String?, lon: String?): LiveData<List<Forecast>>

    @Query("SELECT * FROM forecast WHERE  cityLatitude = :lat AND cityLongitude = :lon")
    fun getForecast(lat: String?, lon: String?): List<Forecast>

    @Query("DELETE FROM forecast WHERE cityLatitude = :lat AND cityLongitude = :lon")
    suspend fun deleteForecast(lat: String?, lon: String?)

    @Transaction
    suspend fun insertAndDeleteWeather(weather: Weather, forecast: ArrayList<Forecast>, lat:String, lon:String) {
        insertWeather(weather)
        deleteForecast(lat, lon)
        insertForecast(forecast)
    }
}