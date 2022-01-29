package com.example.firstapp.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.firstapp.utils.WeatherInteraction
import com.example.firstapp.utils.WeatherPreferences

class WeatherRequestWorker(val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val city = WeatherPreferences.getLocationModel()
        if (city != null) {
            WeatherInteraction.checkWeatherOnApi(city)
            return Result.success()
        }
        return Result.failure()
    }
}