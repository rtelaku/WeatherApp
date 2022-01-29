package com.example.firstapp.ui.cityWeatherDetails

import androidx.lifecycle.LiveData
import com.example.firstapp.db.Forecast
import com.example.firstapp.db.Weather
import com.example.firstapp.model.LocationModel
import com.example.firstapp.utils.WeatherApp

class CityWeatherDetailsRepository {

    fun getWeatherFromDatabase(locationModel: LocationModel): LiveData<Weather> {
            return WeatherApp.getInstance().getDb().weatherDao().getCityWeather(locationModel.lat, locationModel.lon)
    }

    fun getForecastFromDatabase(locationModel: LocationModel): LiveData<List<Forecast>> {
        return WeatherApp.getInstance().getDb().weatherDao().getDbForecast(locationModel.lat, locationModel.lon)
    }
}