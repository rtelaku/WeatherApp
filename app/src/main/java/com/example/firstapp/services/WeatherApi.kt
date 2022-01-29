package com.example.firstapp.services

import com.example.firstapp.model.GetWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("exclude") exclude: String?,
        @Query("appid") appId: String?
    ): GetWeather
}