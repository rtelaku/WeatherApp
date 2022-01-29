package com.example.firstapp.model

import com.example.firstapp.services.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private lateinit var weatherApi: WeatherApi

    fun getWeatherAPI(): WeatherApi {
        if (!this::weatherApi.isInitialized) {
            weatherApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
        return weatherApi
    }

    private const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = "de977e371a64bf10168a6905cf681711"
}