package com.example.firstapp.utils

import com.example.firstapp.db.Forecast
import com.example.firstapp.db.Weather
import com.example.firstapp.model.GetWeather
import com.example.firstapp.model.LocationModel
import java.util.*

object WeatherUtils {

    fun parseCurrentWeatherFromResponse(
        weatherResponse: GetWeather,
        locationModel: LocationModel): Weather {
        val main = weatherResponse.main
        val currentWeatherDetails = main?.weather?.toTypedArray()
        var iconUrl = ""
        var citytemperature = 0
        val temp = main?.temp
        citytemperature = convertKelvinToCelsius(temp)
        val currentCityTemperature = citytemperature.toString()
        if (currentWeatherDetails != null) {
            for (i in currentWeatherDetails.indices) {
                val apiIcon = currentWeatherDetails[0].icon
                iconUrl = "http://openweathermap.org/img/wn/$apiIcon.png"
            }
        }
        return Weather(locationModel.locationName, currentCityTemperature, locationModel.lat, locationModel.lon, iconUrl)
    }

    fun parseForecastWeatherFromResponse(
        weatherResponse: GetWeather,
        weather: Weather): ArrayList<Forecast> {
        val daily = weatherResponse.daily
        var forecastIconUrl = ""
        val forecastList: List<Forecast> = daily.map { day ->
            val forecastWeatherDetails = day.weather?.toTypedArray()
            val apiIcon = forecastWeatherDetails?.get(0)?.icon
            forecastIconUrl = "http://openweathermap.org/img/wn/$apiIcon.png"
            val date = day.dt
            val convertedDate = Date(date!! * 1000L)
            val min = day.temp?.min
            val max = day.temp?.max
            val minTemp = convertKelvinToCelsius(min)
            val maxTemp = convertKelvinToCelsius(max)
            val maxCityTemp = maxTemp.toString()
            val minCityTemp = minTemp.toString()

            Forecast(
                forecastIconUrl,
                convertedDate,
                minCityTemp,
                maxCityTemp,
                weather.cityLatitude,
                weather.cityLongitude
            )
        }
        return forecastList as ArrayList<Forecast>
    }

    private fun convertKelvinToCelsius(temp: Double?): Int {
        return (temp!! - 273.15).toInt()
    }

}