package com.example.firstapp.ui.cityWeatherDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.firstapp.db.Forecast
import com.example.firstapp.db.Weather
import com.example.firstapp.model.LocationModel

class CityWeatherDetailsActivityViewModel : ViewModel() {
    private val cityWeatherDetailsRepository: CityWeatherDetailsRepository = CityWeatherDetailsRepository()

    fun getWeather(locationModel: LocationModel): LiveData<Weather> {
        return cityWeatherDetailsRepository.getWeatherFromDatabase(locationModel)
    }

    fun getForecast(locationModel: LocationModel): LiveData<List<Forecast>> {
        return cityWeatherDetailsRepository.getForecastFromDatabase(locationModel)
    }

}