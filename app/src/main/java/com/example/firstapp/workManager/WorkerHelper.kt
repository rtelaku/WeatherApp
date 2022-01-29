package com.example.firstapp.workManager

import androidx.work.*
import com.example.firstapp.utils.WeatherApp
import java.util.concurrent.TimeUnit

object WorkerHelper {

    fun startWeatherWorker(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work = PeriodicWorkRequestBuilder<WeatherRequestWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        val workManager = WorkManager.getInstance(WeatherApp.getInstance().applicationContext)
        workManager.enqueueUniquePeriodicWork("Worker_FetchWeatherDataWorker", ExistingPeriodicWorkPolicy.REPLACE, work)
    }
}