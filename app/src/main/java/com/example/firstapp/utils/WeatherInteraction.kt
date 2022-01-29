package com.example.firstapp.utils

import androidx.work.ListenableWorker
import com.example.firstapp.db.Forecast
import com.example.firstapp.db.Weather
import com.example.firstapp.db.WeatherDatabase
import com.example.firstapp.model.GetWeather
import com.example.firstapp.model.LocationModel
import com.example.firstapp.model.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

object WeatherInteraction {

    suspend fun checkWeatherOnApi(locationModel: LocationModel) : ListenableWorker.Result{
        val weatherRestApiDeferrable = CoroutineScope(Dispatchers.IO).async {
            return@async retryIO { RetrofitHelper.getWeatherAPI().getWeatherData(locationModel.lat,locationModel.lon, "", RetrofitHelper.API_KEY) }
        }
        return try {
            handleRequest(locationModel, weatherRestApiDeferrable.await())
            ListenableWorker.Result.success()
        } catch (exception: Throwable) {
            exception.printStackTrace()
            ToastUtils.displayToast(WeatherApp.getInstance(), "Please check your internet connection!")
            ListenableWorker.Result.failure()
        }
    }

    private suspend fun handleRequest(locationModel: LocationModel, weatherResponse: GetWeather){
        val currentWeather = WeatherUtils.parseCurrentWeatherFromResponse(
            weatherResponse,
            locationModel)

        val forecastList =
            WeatherUtils.parseForecastWeatherFromResponse(
                weatherResponse,
                currentWeather)
        forecastList.removeAt(0)

        saveWeatherData(currentWeather, forecastList, locationModel)
    }

    private suspend fun saveWeatherData(currentWeather: Weather, forecastList: ArrayList<Forecast>, locationModel: LocationModel) {
        val weatherDatabase: WeatherDatabase = WeatherApp.getInstance().getDb()
        CoroutineScope(Dispatchers.IO).launch {
            weatherDatabase.weatherDao().insertAndDeleteWeather(currentWeather,forecastList,locationModel.lat, locationModel.lon)
        }
    }


}